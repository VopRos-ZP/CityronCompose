package com.vopros.cityron.ui.screens.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.vopros.cityron.NavGraphs
import com.vopros.cityron.destinations.FindControllerScreenDestination
import com.vopros.cityron.destinations.StateScreenDestination
import com.vopros.cityron.ui.theme.ClearRippleTheme
import com.vopros.cityron.ui.theme.LocalDrawer
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun RootScreen() {
    val viewModel: RootViewModel = hiltViewModel()
    val state = viewModel.state

    val scope = rememberCoroutineScope()
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val closeDrawer = { scope.launch { drawerState.close() } }

    val childNavController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        content = {
            CompositionLocalProvider(
                LocalDrawer provides drawerState,
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.drawer,
                    navController = childNavController
                )
            }
        },
        drawerContent = {
            ModalDrawerSheet {
                val text = when (state.controllers.isEmpty()) {
                    true -> "Добавьте контроллеры"
                    else -> "Контроллеры"
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides ClearRippleTheme
                ) {
                    NavigationDrawerItem(
                        label = { Text(text = text) },
                        selected = false,
                        onClick = {}
                    )
                }
                state.controllers.mapIndexed { i, (controller, s) ->
                    val ripple: @Composable (@Composable () -> Unit) -> Unit = {
                        when (s != 0) {
                            true -> CompositionLocalProvider(
                                LocalRippleTheme provides ClearRippleTheme,
                                content = it
                            )
                            else -> it()
                        }
                    }
                    ripple {
                        NavigationDrawerItem(
                            label = {
                                val color = when (s) {
                                    0 -> Color.Green
                                    1 -> Color.Red
                                    2 -> Color.Yellow
                                    else -> Color.Transparent
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = controller.name)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .clip(RoundedCornerShape(percent = 100))
                                            .background(color)
                                    )
                                }
                            },
                            selected = selected == i,
                            onClick = {
                                if (s == 0) {
                                    selected = i
                                    viewModel.setController(controller)
                                    childNavController.navigate(StateScreenDestination)
                                    closeDrawer()
                                }
                            }
                        )
                    }
                }
                NavigationDrawerItem(
                    label = { Text(text = "Найти контроллер") },
                    selected = selected == state.controllers.size,
                    onClick = {
                        selected = state.controllers.size
                        closeDrawer()
                        childNavController.navigate(FindControllerScreenDestination)
                    }
                )
            }
        }
    )
    LaunchedEffect(Unit) {
        viewModel.fetchControllers()
    }
}