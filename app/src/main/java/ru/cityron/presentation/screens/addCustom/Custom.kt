package ru.cityron.presentation.screens.addCustom

object Custom {

    data class State(
        val ip: String = "",
        val isCorrect: Boolean = false,
        val isErrorChecked: Boolean? = null,
        val isShowSnackbar: Boolean = false
    )

}