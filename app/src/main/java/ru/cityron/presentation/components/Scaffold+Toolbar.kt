package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import ru.cityron.presentation.mvi.SnackbarResult

@Composable
fun <T> DrawerScaffoldWithState(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSettingsClick: (() -> Unit)? = null,
    fab: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarResult: SnackbarResult? = null,
    onDismissSnackbar: SnackbarResult.() -> Unit = {},
    state: State<T?>,
    content: @Composable BoxScope.(T) -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { DrawerTopBar(title = title, onClick = onClick, onSettingsClick = onSettingsClick) },
        snackbarResult = snackbarResult,
        fab = fab,
        bottomBar = bottomBar,
        onDismissSnackbar = onDismissSnackbar,
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
    snackbarResult: SnackbarResult? = null,
    onDismissSnackbar: SnackbarResult.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { DrawerTopBar(title = title, onClick = onClick, onSettingsClick = onSettingsClick) },
        snackbarResult = snackbarResult,
        fab = fab,
        bottomBar = bottomBar,
        onDismissSnackbar = onDismissSnackbar,
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
    snackbarResult: SnackbarResult? = null,
    onDismissSnackbar: SnackbarResult.() -> Unit = {},
    state: State<T?>,
    content: @Composable BoxScope.(T) -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarResult = snackbarResult,
        fab = fab,
        bottomBar = bottomBar,
        onDismissSnackbar = onDismissSnackbar,
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
    snackbarResult: SnackbarResult? = null,
    onDismissSnackbar: SnackbarResult.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        modifier = modifier,
        topBar = { BackTopBar(title = title, onClick = onClick) },
        snackbarResult = snackbarResult,
        fab = fab,
        bottomBar = bottomBar,
        onDismissSnackbar = onDismissSnackbar,
        content = content
    )
}

@Composable
fun ToolbarScaffold(
    modifier: Modifier,
    topBar: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
    bottomBar: @Composable () -> Unit = {},
    snackbarResult: SnackbarResult? = null,
    onDismissSnackbar: SnackbarResult.() -> Unit = {},
    fab: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = topBar,
        snackbarHost = {
            snackbarResult?.let { r ->
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { Snackbar(r, it) }
                )
            }
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
    LaunchedEffect(snackbarResult) {
        if (snackbarResult != null) {
            if (snackbarHostState.showSnackbar(message = ctx.getString(snackbarResult.label))
                == androidx.compose.material.SnackbarResult.Dismissed) {
                onDismissSnackbar(snackbarResult)
            }
        }
    }
}