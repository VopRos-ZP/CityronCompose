package ru.cityron.presentation.screens.algo.electric

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoElectricViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoElectricViewAction
}