package com.vopros.cityron.ui.components.tab

import androidx.compose.runtime.Composable

data class ControllerPagerTab(
    val title: String,
    val content: @Composable () -> Unit
)
