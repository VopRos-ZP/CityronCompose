package ru.cityron.presentation.screens.find

import ru.cityron.domain.model.Controller

data class FindViewState(
    val controllers: Map<Controller, Boolean> = emptyMap(),
)
