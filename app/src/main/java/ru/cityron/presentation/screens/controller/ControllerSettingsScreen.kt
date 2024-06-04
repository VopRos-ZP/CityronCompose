package ru.cityron.presentation.screens.controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.components.BackScaffold

@Composable
fun ControllerSettingsScreen(
    onClick: () -> Unit,
) {
    BackScaffold(
        title = "Контроллер",
        onClick = onClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

        }
    }
}