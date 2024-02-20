package com.vopros.cityron.ui.screens.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.destinations.M3ScreenDestination
import com.vopros.cityron.navigation.MainNavGraph
import com.vopros.cityron.ui.components.ControllerCard
import com.vopros.cityron.ui.components.Loading
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@MainNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val controllersState = viewModel.controllers.collectAsState()
    when (val controllers = controllersState.value) {
        null -> Loading()
        emptyList<ControllerItem>() -> {

        }
        else -> {
            LazyColumn {
                items(controllers) {
                    ControllerCard(item = it) {
                        navigator.navigate(M3ScreenDestination(Json.encodeToString(it)))
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchControllers()
    }
}