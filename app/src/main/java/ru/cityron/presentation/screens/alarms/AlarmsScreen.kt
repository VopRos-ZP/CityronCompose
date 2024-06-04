package ru.cityron.presentation.screens.alarms

import androidx.compose.runtime.Composable
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AlarmsScreen(
    onClick: () -> Unit
) {
    BackScaffold(
        title = "Аварии",
        onClick = onClick
    ) {

    }
}