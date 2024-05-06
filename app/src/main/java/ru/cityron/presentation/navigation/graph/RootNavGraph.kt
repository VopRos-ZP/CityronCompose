package ru.cityron.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.cityron.domain.model.Controller
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.screens.blank.BlankScreen
import ru.cityron.presentation.screens.find.FindScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    controllers: List<Controller>,
    openDrawer: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Blank.route
    ) {
        composable(Screen.Blank.route) { BlankScreen(openDrawer) }
        composable(Screen.Find.route) { FindScreen(openDrawer) }
        controllers.forEach { controller ->
            if (controller.name.startsWith("M3")) {
                m3NavGraph(controller.name, openDrawer)
            }
        }
    }
}
