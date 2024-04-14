package com.vopros.cityron.m3.screen

import ru.cityron.m3.domain.model.M3All

data class M3ViewState(
    val isLoading: Boolean = false,
    val all: M3All? = null,
    val message: String? = null
)
