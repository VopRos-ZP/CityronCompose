package com.vopros.cityron.ui.screens.root

import ru.cityron.core.domain.model.Controller

data class RootViewState(
    val isLoading: Boolean = false,
    val controllers: List<Pair<Controller, Int>> = emptyList(),
    val message: String? = null
)
