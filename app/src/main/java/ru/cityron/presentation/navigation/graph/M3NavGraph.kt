package ru.cityron.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.slideInOutComposable
import ru.cityron.presentation.screens.alarms.AlarmsScreen
import ru.cityron.presentation.screens.alerts.AlertsScreen
import ru.cityron.presentation.screens.changeName.ChangeNameScreen
import ru.cityron.presentation.screens.editAlarm.EditAlarmScreen
import ru.cityron.presentation.screens.editScheduler.EditSchedulerScreen
import ru.cityron.presentation.screens.events.filters.EventFiltersScreen
import ru.cityron.presentation.screens.m3tabs.M3TabsScreen
import ru.cityron.presentation.screens.scheduler.SchedulersScreen
import ru.cityron.presentation.screens.settings.SettingsScreen

fun NavGraphBuilder.m3NavGraph(
    route: String,
    onDrawer: () -> Unit,
    onBack: () -> Unit,
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    onChangeName: () -> Unit,
    onAuthClick: () -> Unit,
    onAlgoClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onControllerClick: () -> Unit,
    onFilterClick: () -> Unit,
    onEditAlarmClick: (Int) -> Unit,
) {
    navigation(
        startDestination = Screen.M3Tabs.route,
        route = route
    ) {
        composable(route = Screen.M3Tabs.route) {
            M3TabsScreen(
                onClick = onDrawer,
                onAlertsClick = onAlertsClick,
                onSchedulerClick = onSchedulerClick,
                onSettingsClick = onSettingsClick,
                onFilterClick = onFilterClick
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
        slideInOutComposable(
            route = "${Screen.Alarms.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditAlarmScreen(
                onClick = onBack,
                id = it.arguments?.getInt("id") ?: 0
            )
        }
        slideInOutComposable(route = Screen.Settings.route) {
            SettingsScreen(
                onClick = onBack,
                onChangeName = onChangeName,
                onAuthClick = onAuthClick,
                onAlgoClick = onAlgoClick,
                onAlarmClick = onAlarmClick,
                onControllerClick = onControllerClick
            )
        }
        slideInOutComposable(route = Screen.ChangeName.route) {
            ChangeNameScreen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.Filters.route) {
            EventFiltersScreen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.Alarms.route) {
            AlarmsScreen(
                onClick = onBack,
                onEditAlarmClick = onEditAlarmClick
            )
        }
    }
}