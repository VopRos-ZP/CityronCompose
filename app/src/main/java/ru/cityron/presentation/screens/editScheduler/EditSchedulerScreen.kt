package ru.cityron.presentation.screens.editScheduler

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffold

@Composable
fun EditSchedulerScreen(
    onClick: () -> Unit,
    viewModel: EditSchedulerViewModel = hiltViewModel()
) {
    BackScaffold(
        title = "",
        onClick = onClick
    ) {

    }
}