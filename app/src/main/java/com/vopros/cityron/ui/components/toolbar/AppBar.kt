package com.vopros.cityron.ui.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.vopros.cityron.ui.theme.LocalToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(onNavIconClick: () -> Unit) {
    val viewModel = LocalToolbar.current
    TopAppBar(
        title = { Text(text = viewModel.title) },
        navigationIcon = {
            val (icon, onClick) = when (viewModel.isShowBack) {
                true -> Pair(Icons.AutoMirrored.Filled.ArrowBack) {
                    viewModel.navController?.navigateUp(); Unit
                }
                else -> Pair(Icons.Default.Menu, onNavIconClick)
            }
            IconButton(onClick = onClick) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    )
}