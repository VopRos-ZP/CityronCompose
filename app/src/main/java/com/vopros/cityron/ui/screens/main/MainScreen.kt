package com.vopros.cityron.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vopros.cityron.NavGraphs
import com.vopros.cityron.ui.components.drawer.NavDrawer
import com.vopros.cityron.ui.components.toolbar.Toolbar
import com.vopros.cityron.ui.theme.LocalToolbar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val controllers by viewModel.controllers.collectAsState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    NavDrawer(
        navController = navController,
        state = drawerState,
        controllers = controllers
    ) {
        CompositionLocalProvider(
            LocalToolbar provides hiltViewModel()
        ) {
            Scaffold(
                topBar = { Toolbar { scope.launch { drawerState.open() } } },
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchControllers()
    }
}