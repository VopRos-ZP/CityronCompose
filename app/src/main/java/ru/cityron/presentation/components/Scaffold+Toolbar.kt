package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DrawerScaffold(
    title: String,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        topBar = { DrawerTopBar(title = title, onClick = onClick) },
        content = content
    )
}

@Composable
fun BackScaffold(
    title: String,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    ToolbarScaffold(
        topBar = { BackTopBar(title = title, onClick = onClick) },
        content = content
    )
}

@Composable
fun ToolbarScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(topBar = topBar) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}