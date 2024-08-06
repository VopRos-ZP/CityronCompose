package ru.cityron.presentation.mvi

import androidx.annotation.StringRes

data class SnackbarResult(
    @StringRes val label: Int,
    val isError: Boolean,
)
