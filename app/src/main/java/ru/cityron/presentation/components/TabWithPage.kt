package ru.cityron.presentation.components

import androidx.compose.runtime.Composable

data class TabWithPage(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit
)