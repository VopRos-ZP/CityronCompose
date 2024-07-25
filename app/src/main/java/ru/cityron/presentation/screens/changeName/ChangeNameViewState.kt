package ru.cityron.presentation.screens.changeName

import ru.cityron.domain.model.Controller

data class ChangeNameViewState(
    val name: String = "",
    val oldName: String = "",
    val isChanged: Boolean = false,
    val isErrorChecked: Boolean? = null,
    val isShowSnackbar: Boolean = false
)
