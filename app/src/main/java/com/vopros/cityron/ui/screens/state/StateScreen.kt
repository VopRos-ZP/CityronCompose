package com.vopros.cityron.ui.screens.state

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vopros.cityron.NavGraphs
import com.vopros.cityron.destinations.M3ScreenDestination
import com.vopros.cityron.ui.navigation.ControllerNavGraph
import com.vopros.cityron.ui.navigation.DrawerNavGraph
import com.vopros.cityron.ui.screens.root.RootViewModel

@DrawerNavGraph
@Destination
@Composable
fun StateScreen() {
    DestinationsNavHost(navGraph = NavGraphs.controller)
}

@ControllerNavGraph(start = true)
@Destination
@Composable
fun ControllerProviderScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: RootViewModel = hiltViewModel()
    when (viewModel.controller?.name?.split(" ")?.get(0)) {
        "M3" -> navigator.navigate(M3ScreenDestination)
        else -> {}
    }
}