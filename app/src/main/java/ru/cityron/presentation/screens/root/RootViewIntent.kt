package ru.cityron.presentation.screens.root

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource

sealed interface RootViewIntent {
    data class OnSelectController(val value: Pair<Controller, DataSource>) : RootViewIntent
}