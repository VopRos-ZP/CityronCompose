package com.vopros.cityron.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.vopros.cityron.ui.components.toolbar.ToolbarViewModel

val LocalToolbar = compositionLocalOf<ToolbarViewModel> {
    error("Toolbar viewModel doesn't provides")
}