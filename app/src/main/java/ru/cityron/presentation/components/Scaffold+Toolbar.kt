package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DrawerScaffold(
    title: String,
    onClick: () -> Unit,
    fab: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        topBar = { DrawerTopBar(title = title, onClick = onClick) },
        snackbarHostState = snackbarHostState,
        fab = fab,
        content = content
    )
}

@Composable
fun BackScaffold(
    title: String,
    onClick: () -> Unit,
    fab: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarHostState = snackbarHostState,
        fab = fab,
        content = content
    )
}

@Composable
fun ToolbarScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
    snackbarHostState: SnackbarHostState,
    fab: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { Snackbar(it) }
            )
        },
        floatingActionButton = fab
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
            content = content
        )
    }

}