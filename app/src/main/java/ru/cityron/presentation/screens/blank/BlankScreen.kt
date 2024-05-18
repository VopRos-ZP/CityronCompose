package ru.cityron.presentation.screens.blank

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun BlankScreen(onClick: () -> Unit) {
    LaunchedEffect(key1 = Unit) { onClick() }
}