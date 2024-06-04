package ru.cityron.presentation.screens.auth

import androidx.compose.runtime.Composable
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AuthScreen(
    onClick: () -> Unit
) {
    BackScaffold(
        title = "Сменить пользователя",
        onClick = onClick
    ) {

    }
}