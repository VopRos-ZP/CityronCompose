package ru.cityron.presentation.screens.changeName

import ru.cityron.presentation.mvi.SnackbarResult

data class ChangeNameViewState(
    val name: String = "",
    val oldName: String = "",
    val isChanged: Boolean = false,
    val result: SnackbarResult? = null,
)
