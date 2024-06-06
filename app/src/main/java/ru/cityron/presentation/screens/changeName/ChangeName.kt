package ru.cityron.presentation.screens.changeName

object ChangeName {

    data class State(
        val name: String = "",
        val oldName: String = "",
        val isChanged: Boolean = false,
        val isErrorChecked: Boolean? = null,
        val isShowSnackbar: Boolean = false
    )

}