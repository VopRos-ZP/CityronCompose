package ru.cityron.presentation.screens.m3tabs

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.m3.M3State

sealed interface M3TabsState {

    data object Loading : M3TabsState

    data class Error(val message: String): M3TabsState
    data class Success(
        val controller: Controller,
        val state: M3State
    ) : M3TabsState

}
