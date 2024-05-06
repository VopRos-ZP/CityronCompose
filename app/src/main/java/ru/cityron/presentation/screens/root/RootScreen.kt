package ru.cityron.presentation.screens.root

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

@Composable
fun RootScreen() {
    val scope = rememberCoroutineScope()
    val navigationState = rememberNavigationState()
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val viewModel: RootViewModel = hiltViewModel()
    val controllers by viewModel.controllers.collectAsState()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
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
            DrawerItem(
                text = "Поиск контроллера",
                selected = navBackStackEntry?.destination?.route == Screen.Find.route,
                onClick = {
                    navigationState.navigateTo(Screen.Find.route)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        RootNavGraph(
            navHostController = navigationState.navHostController,
            controllers = controllers.keys.toList(),
            openDrawer = { scope.launch { drawerState.open() } }
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
        action = {
            val color = when (source.status) {
                Status.ONLINE -> Color.Green
                Status.OFFLINE -> Color.Yellow
                Status.ALERT -> Color.Red
            }
            val icon = when {
                source.status == Status.OFFLINE -> Icons.Default.CloudOff
                source is DataSource.Local -> Icons.Default.Wifi
                source is DataSource.Remote -> Icons.Default.CloudQueue
                else -> throw RuntimeException("")
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
        }
    )
}

@Composable
fun DrawerItem(
    text: String,
    action: @Composable () -> Unit = {},
    enabled: Boolean = true,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val (color, textColor) = when (selected) {
        true -> MaterialTheme.colors.primary.copy(alpha = 0.3f) to MaterialTheme.colors.primary
        else -> MaterialTheme.colors.background to MaterialTheme.colors.onBackground
    }
    Row(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(percent = 20))
            .background(color)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = enabled && !selected,
                onClick = onClick
            )
            .padding(vertical = 10.dp, horizontal = 15.dp)
    ) {
        Text(text = text, color = textColor)
        Spacer(modifier = Modifier.weight(1f))
        action()
    }
}
