package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier

@Composable
fun DrawerScaffold(
    title: String,
    onClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { DrawerTopBar(title = title, onClick = onClick, onSettingsClick = onSettingsClick) },
        snackbarHostState = snackbarHostState,
        fab = fab,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun BackScaffold(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarHostState = snackbarHostState,
        fab = fab,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun ToolbarScaffold(
    modifier: Modifier,
    topBar: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
    bottomBar: @Composable () -> Unit = {},
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
        floatingActionButton = fab,
        bottomBar = bottomBar
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
            content = content
        )
    }

}