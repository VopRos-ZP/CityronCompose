package ru.cityron.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.screens.m3tabs.M3TabsScreen

fun NavGraphBuilder.m3NavGraph(route: String, onClick: () -> Unit) {
    navigation(
        startDestination = Screen.M3Tabs.route,
        route = route
    ) {
        composable(route = Screen.M3Tabs.route) {
            M3TabsScreen(onClick = onClick)
        }
        composable(route = Screen.Schedulers.route) {

        }
        composable(route = Screen.Alerts.route) {

        }
    }
}