package ru.cityron.presentation.screens.root

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource

sealed interface RootViewIntent {
    data object Launch : RootViewIntent
    data class OnSelectController(val value: Controller) : RootViewIntent
}