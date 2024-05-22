package ru.cityron.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.slideInOutComposable
import ru.cityron.presentation.screens.alerts.AlertsScreen
import ru.cityron.presentation.screens.editScheduler.EditSchedulerScreen
import ru.cityron.presentation.screens.m3tabs.M3TabsScreen
import ru.cityron.presentation.screens.scheduler.SchedulersScreen

fun NavGraphBuilder.m3NavGraph(
    route: String,
    onDrawer: () -> Unit,
    onBack: () -> Unit,
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
) {
    navigation(
        startDestination = Screen.M3Tabs.route,
        route = route
    ) {
        composable(route = Screen.M3Tabs.route) {
            M3TabsScreen(
                onClick = onDrawer,
                onAlertsClick = onAlertsClick,
                onSchedulerClick = onSchedulerClick
            )
        }
        slideInOutComposable(route = Screen.Schedulers.route) {
            SchedulersScreen(
                onClick = onBack,
                onTaskClick = onTaskClick
            )
        }
        slideInOutComposable(route = Screen.Alerts.route) {
            AlertsScreen(onClick = onBack)
        }
        slideInOutComposable(
            route = "${Screen.Schedulers.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditSchedulerScreen(
                onClick = onBack,
                id = it.arguments?.getInt("id") ?: 0
                )
        }
    }
}