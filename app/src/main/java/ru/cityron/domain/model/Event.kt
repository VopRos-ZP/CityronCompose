package ru.cityron.domain.model

import ru.cityron.data.room.all.Table

data class Event(
    val id: Int = Table.ID,
    val date: String,
    val time: String,
    val type: Int,
    val text: String
)
