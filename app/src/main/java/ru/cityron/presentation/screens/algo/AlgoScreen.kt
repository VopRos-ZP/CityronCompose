package ru.cityron.presentation.screens.algo

import androidx.compose.runtime.Composable
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AlgoScreen(
    onClick: () -> Unit
) {
    BackScaffold(
        title = "Алгоритм",
        onClick = onClick
    ) {

    }
}