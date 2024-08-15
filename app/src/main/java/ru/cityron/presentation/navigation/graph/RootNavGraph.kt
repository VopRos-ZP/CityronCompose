package ru.cityron.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ru.cityron.domain.model.Controller
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.SlideInOutNavHost
import ru.cityron.presentation.navigation.slideInOutComposable
import ru.cityron.presentation.screens.blank.BlankScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    controllers: List<Controller>,
    onDrawer: () -> Unit,
    onBack: () -> Unit,
    onCustomClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    onChangeName: () -> Unit,
    onAuthClick: () -> Unit,
    onNavigateClick: (Screen) -> Unit,
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
    onEthClick: () -> Unit,
    onWebClick: () -> Unit,
    onMetricClick: () -> Unit,
) {
    SlideInOutNavHost(
        navController = navHostController,
        startDestination = Screen.Blank.route
    ) {
        slideInOutComposable(Screen.Blank.route) { BlankScreen(onDrawer) }
        findNavGraph(
            onDrawer = onDrawer,
            onBack = onBack,
            onCustomClick = onCustomClick,
            onAuthClick = onAuthClick,
            onNavigateClick = onNavigateClick
        )
        controllers.forEach { controller ->
            if (controller.name.startsWith("M3")) {
                m3NavGraph(
                    route = controller.idCpu,
                    onDrawer = onDrawer,
                    onBack = onBack,
                    onAlertsClick = onAlertsClick,
                    onSchedulerClick = onSchedulerClick,
                    onTaskClick = onTaskClick,
                    onSettingsClick = onSettingsClick,
                    onChangeName = onChangeName,
                    onAuthClick = onAuthClick,
                    onAlgoClick = onAlgoClick,
                    onAlarmClick = onAlarmClick,
                    onControllerClick = onControllerClick,
                    onFilterClick = onFilterClick,
                    onEditAlarmClick = onEditAlarmClick,
                    onTimingsClick = onTimingsClick,
                    onFan1Click = onFan1Click,
                    onFan2Click = onFan2Click,
                    onPi1Click = onPi1Click,
                    onPi2Click = onPi2Click,
                    onElectricClick = onElectricClick,
                    onWaterClick = onWaterClick,
                    onOtherClick = onOtherClick,
                    onDatetimeClick = onDatetimeClick,
                    onEthClick = onEthClick,
                    onWebClick = onWebClick,
                    onMetricClick = onMetricClick
                )
            }
        }
    }
}
