package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun <T> DrawerScaffoldWithState(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSettingsClick: (() -> Unit)? = null,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarState: SnackbarState = rememberSnackbarState(),
    state: State<T?>,
    content: @Composable BoxScope.(T) -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { DrawerTopBar(title = title, onClick = onClick, onSettingsClick = onSettingsClick) },
        snackbarState = snackbarState,
        fab = fab,
        bottomBar = bottomBar,
        content = {
            when (val value = state.value) {
                null -> Loader()
                else -> content(value)
            }
        }
    )
}

@Composable
fun DrawerScaffold(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSettingsClick: (() -> Unit)? = null,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarState: SnackbarState = rememberSnackbarState(),
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { DrawerTopBar(title = title, onClick = onClick, onSettingsClick = onSettingsClick) },
        snackbarState = snackbarState,
        fab = fab,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun <T> BackScaffoldWithState(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarState: SnackbarState = rememberSnackbarState(),
    state: State<T?>,
    content: @Composable BoxScope.(T) -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarState = snackbarState,
        fab = fab,
        bottomBar = bottomBar,
        content = {
            when (val value = state.value) {
                null -> Loader()
                else -> content(value)
            }
        }
    )
}

@Composable
fun BackScaffold(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarState: SnackbarState = rememberSnackbarState(),
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarState = snackbarState,
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
    snackbarState: SnackbarState,
    fab: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState.snackbarHostState,
                snackbar = { Snackbar(snackbarState.snackbarResult, it) }
            )
        },
        floatingActionButton = fab,
        bottomBar = bottomBar,
        backgroundColor = Color.Transparent
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