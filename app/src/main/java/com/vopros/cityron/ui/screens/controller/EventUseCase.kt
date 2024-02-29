package com.vopros.cityron.ui.screens.controller

import java.time.LocalDateTime

data class EventUseCase(
    val date: LocalDateTime,
    val type: String,
    val result: String
)
