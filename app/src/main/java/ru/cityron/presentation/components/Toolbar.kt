package ru.cityron.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DrawerTopBar(
    title: String,
    onClick: () -> Unit
) {
    ToolBar(
        title = title,
        icon = Icons.Default.Menu,
        onClick = onClick
    )
}

@Composable
fun BackTopBar(
    title: String,
    onClick: () -> Unit
) {
    ToolBar(
        title = title,
        icon = Icons.AutoMirrored.Default.ArrowBack,
        onClick = onClick
    )
}

@Composable
private fun ToolBar(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }
    )
}