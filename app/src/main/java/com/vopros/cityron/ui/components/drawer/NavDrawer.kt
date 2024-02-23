package com.vopros.cityron.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.destinations.FindControllerScreenDestination
import com.vopros.cityron.destinations.M3ScreenDestination
import com.vopros.cityron.ui.theme.ClearRippleTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun NavDrawer(
    navController: NavHostController,
    state: DrawerState,
    controllers: List<Pair<ControllerItem, Int>>,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val closeDrawer = { scope.launch { state.close() } }
    var selected by remember { mutableStateOf<Int?>(null) }
    ModalNavigationDrawer(
        drawerState = state,
        content = content,
        drawerContent = {
            ModalDrawerSheet {
                val text = when (controllers.size) {
                    0 -> "Добавьте контроллеры"
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
                controllers.mapIndexed { i, (controller, s) ->
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
                            selected = i
                            closeDrawer()
                            navController.navigate(M3ScreenDestination(Json.encodeToString(controller))) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                NavigationDrawerItem(
                    label = { Text(text = "Найти контроллер") },
                    selected = selected == controllers.size,
                    onClick = {
                        selected = controllers.size
                        closeDrawer()
                        navController.navigate(FindControllerScreenDestination()) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    )
}