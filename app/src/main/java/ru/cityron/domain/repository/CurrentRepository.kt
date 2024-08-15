package ru.cityron.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.cityron.domain.model.Controller

interface CurrentRepository {
    val current: StateFlow<Controller?>
    fun setCurrentController(current: Controller?)
}