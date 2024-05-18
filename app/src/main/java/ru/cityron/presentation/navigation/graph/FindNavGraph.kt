package ru.cityron.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.cityron.domain.model.Controller
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.slideInOutComposable
import ru.cityron.presentation.screens.addController.AddControllerScreen
import ru.cityron.presentation.screens.addCustom.AddCustomScreen
import ru.cityron.presentation.screens.find.FindScreen

fun NavGraphBuilder.findNavGraph(
    onDrawer: () -> Unit,
    onBack: () -> Unit,
    onAddClick: (Controller) -> Unit,
    onCustomClick: () -> Unit,
) {
    navigation(
        startDestination = Screen.Find.route,
        route = Screen.FindFlow.route
    ) {
        composable(route = Screen.Find.route) {
            FindScreen(
                onClick = onDrawer,
                onAddClick = onAddClick,
                onCustomClick = onCustomClick
            )
        }
        slideInOutComposable(route = Screen.AddController.route) {
            AddControllerScreen(
                onClick = onBack,
                // onLoginClick = onLoginClick
            )
        }
        slideInOutComposable(route = Screen.AddCustom.route) {
            AddCustomScreen(
                onClick = onBack,
                onNextClick = onBack
            )
        }
    }
}
