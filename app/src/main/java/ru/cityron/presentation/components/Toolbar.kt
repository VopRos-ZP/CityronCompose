package ru.cityron.presentation.components

import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
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
        onActionClick = onSettingsClick
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
        onClick = onClick,
    )
}

@Composable
private fun ToolBar(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    onActionClick: (() -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        content = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            val enabled = onActionClick != null
            IconButton(
                onClick = onActionClick ?: {},
                enabled = enabled
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (enabled) MaterialTheme.colors.onPrimary else Color.Transparent
                )
            }
        },
        elevation = 0.dp
    )
}