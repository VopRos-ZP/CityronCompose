package ru.cityron.presentation.screens.changeName

import androidx.compose.runtime.Composable
import ru.cityron.presentation.components.BackScaffold

@Composable
fun ChangeNameScreen(
    onClick: () -> Unit,
) {
    BackScaffold(
        title = "Имя контроллера",
        onClick = onClick
    ) {

    }
}