package ru.cityron.presentation.screens.find

import ru.cityron.domain.model.Controller

sealed interface FindViewIntent {
    data class OnAddClick(val value: Controller) : FindViewIntent
}