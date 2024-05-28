package ru.cityron.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.cityron.domain.model.Controller
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.screens.blank.BlankScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    controllers: List<Controller>,
    onDrawer: () -> Unit,
    onBack: () -> Unit,
    onAddClick: (Controller) -> Unit,
    onCustomClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Blank.route
    ) {
        composable(Screen.Blank.route) { BlankScreen(onDrawer) }
        findNavGraph(
            onDrawer = onDrawer,
            onBack = onBack,
            onAddClick = onAddClick,
            onCustomClick = onCustomClick
        )
        controllers.forEach { controller ->
            if (controller.name.startsWith("M3")) {
                m3NavGraph(
                    route = controller.name,
                    onDrawer = onDrawer,
                    onBack = onBack,
                    onAlertsClick = onAlertsClick,
                    onSchedulerClick = onSchedulerClick,
                    onTaskClick = onTaskClick,
                    onSettingsClick = onSettingsClick
                )
            }
        }
    }
}
