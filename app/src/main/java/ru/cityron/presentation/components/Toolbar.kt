package ru.cityron.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.cityron.R

@Composable
fun DrawerTopBar(
    title: String,
    onClick: () -> Unit,
    onSettingsClick: (() -> Unit)? = null,
) {
    ToolBar(
        title = title,
        icon = Icons.Default.Menu,
        onClick = onClick,
        actions = {
            if (onSettingsClick != null)
            IconButton(onClick = onSettingsClick) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
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
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        },
        actions = actions,
        elevation = 0.dp
    )
}