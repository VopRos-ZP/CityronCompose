package ru.cityron.presentation.screens.root

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.Status
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.graph.RootNavGraph
import ru.cityron.presentation.navigation.rememberNavigationState
import ru.cityron.ui.theme.Green
import ru.cityron.ui.theme.Orange
import ru.cityron.ui.theme.Red

@Composable
fun RootScreen() {
    val scope = rememberCoroutineScope()
    val navigationState = rememberNavigationState()
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val viewModel: RootViewModel = hiltViewModel()
    val controllers by viewModel.controllers.collectAsState()

    ModalDrawer(
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
        scrimColor = Color.Transparent,
        drawerBackgroundColor = MaterialTheme.colors.primary,
        drawerElevation = 1.dp,
        gesturesEnabled = false,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "M3 - cити",
                    fontSize = 36.sp,
                    color = MaterialTheme.colors.secondary
                )
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                controllers.forEach { (controller, source) ->
                    ControllerDrawerItem(
                        controller = controller,
                        source = source,
                        selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == controller.name } ?: false,
                        onClick = {
                            viewModel.selectController(controller to source)
                            navigationState.navigateTo(controller.name)
                            scope.launch { drawerState.close() }
                        }
                    )
                }
                Scaffold(
                    backgroundColor = MaterialTheme.colors.primary,
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navigationState.navigateTo(Screen.FindFlow.route)
                            scope.launch { drawerState.close() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    },
                    content = { Spacer(modifier = Modifier.padding(it)) }
                )
            }
        }
    ) {
        RootNavGraph(
            navHostController = navigationState.navHostController,
            controllers = controllers.keys.toList(),
            onDrawer = { scope.launch { drawerState.open() } },
            onBack = { navigationState.navigateUp() },
            onAddClick = { navigationState.navigate(Screen.AddController.route) },
            onCustomClick = { navigationState.navigate(Screen.AddCustom.route) },
            onAlertsClick = { navigationState.navigate(Screen.Alerts.route) },
            onSchedulerClick = { navigationState.navigate(Screen.Schedulers.route) },
            onTaskClick = { navigationState.navigate(Screen.Task(it).route) },
            onSettingsClick = { navigationState.navigate(Screen.Settings.route) },
            onChangeName = {},
            onAuthClick = {},
            onAlgoClick = {},
            onAlarmClick = {},
            onControllerClick = {}
        )
    }
    LaunchedEffect(Unit) {
        viewModel.fetchControllers()
    }
}

@Composable
fun ControllerDrawerItem(
    controller: Controller,
    source: DataSource,
    selected: Boolean,
    onClick: () -> Unit
) {
    DrawerItem(
        text = controller.name,
        onClick = onClick,
        enabled = source.status != Status.OFFLINE,
        selected = selected,
        status = when (source.status) {
            Status.ONLINE -> Green
            Status.OFFLINE -> Orange
            Status.ALERT -> Red
        }
    )
}

@Composable
fun DrawerItem(
    text: String,
    status: Color,
    enabled: Boolean = true,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val (color, textColor) = when (selected) {
        true -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f) to MaterialTheme.colors.primaryVariant
        else -> MaterialTheme.colors.primaryVariant to MaterialTheme.colors.primary
    }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 20))
            .background(color)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = enabled && !selected,
                onClick = onClick
            )
            .padding(22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = textColor)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(color = status)
        )
    }
}
