package com.vopros.cityron.ui.theme

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.compositionLocalOf
import com.vopros.cityron.ui.components.toolbar.ToolbarViewModel

val LocalToolbar = compositionLocalOf<ToolbarViewModel> {
    error("Toolbar viewModel doesn't provides")
}

val LocalDrawer = compositionLocalOf<DrawerState> {
    error("NavigationDrawer doesn't provides")
}