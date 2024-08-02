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
import ru.cityron.presentation.screens.algo.AlgoScreen
import ru.cityron.presentation.screens.algo.electric.AlgoElectricScreen
import ru.cityron.presentation.screens.algo.fan1.AlgoFan1Screen
import ru.cityron.presentation.screens.algo.fan2.AlgoFan2Screen
import ru.cityron.presentation.screens.algo.other.AlgoOtherScreen
import ru.cityron.presentation.screens.algo.pi1.AlgoPi1Screen
import ru.cityron.presentation.screens.algo.pi2.AlgoPi2Screen
import ru.cityron.presentation.screens.algo.timings.AlgoTimingsScreen
import ru.cityron.presentation.screens.algo.water.AlgoWaterScreen
import ru.cityron.presentation.screens.changeName.ChangeNameScreen
import ru.cityron.presentation.screens.controller.ControllerSettingsScreen
import ru.cityron.presentation.screens.controller.datetime.ControllerDatetimeScreen
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
    onTimingsClick: () -> Unit,
    onFan1Click: () -> Unit,
    onFan2Click: () -> Unit,
    onPi1Click: () -> Unit,
    onPi2Click: () -> Unit,
    onElectricClick: () -> Unit,
    onWaterClick: () -> Unit,
    onOtherClick: () -> Unit,
    onDatetimeClick: () -> Unit,
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

        slideInOutComposable(route = Screen.Algo.route) {
            AlgoScreen(
                onClick = onBack,
                onTimingsClick = onTimingsClick,
                onFan1Click = onFan1Click,
                onFan2Click = onFan2Click,
                onPi1Click = onPi1Click,
                onPi2Click = onPi2Click,
                onElectricClick = onElectricClick,
                onWaterClick = onWaterClick,
                onOtherClick = onOtherClick
            )
        }
        slideInOutComposable(route = Screen.AlgoTimings.route) {
            AlgoTimingsScreen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoFan1.route) {
            AlgoFan1Screen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoFan2.route) {
            AlgoFan2Screen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoPi1.route) {
            AlgoPi1Screen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoPi2.route) {
            AlgoPi2Screen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoWater.route) {
            AlgoWaterScreen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoElectric.route) {
            AlgoElectricScreen(onClick = onBack)
        }
        slideInOutComposable(route = Screen.AlgoOther.route) {
            AlgoOtherScreen(onClick = onBack)
        }

        slideInOutComposable(route = Screen.Controller.route) {
            ControllerSettingsScreen(
                onClick = onBack,
                onDatetimeClick = onDatetimeClick
            )
        }
        slideInOutComposable(route = Screen.ControllerDatetime.route) {
            ControllerDatetimeScreen(onClick = onBack)
        }
    }
}