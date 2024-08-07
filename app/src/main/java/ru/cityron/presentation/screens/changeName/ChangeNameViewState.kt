package ru.cityron.presentation.screens.changeName

data class ChangeNameViewState(
    val oldName: String = "",
    val name: String = oldName,
    val isChanged: Boolean = false,
)
