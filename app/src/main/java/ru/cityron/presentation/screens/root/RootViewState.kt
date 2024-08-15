package ru.cityron.presentation.screens.root

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource

data class RootViewState(
    val controllers: List<Controller> = emptyList()
)
