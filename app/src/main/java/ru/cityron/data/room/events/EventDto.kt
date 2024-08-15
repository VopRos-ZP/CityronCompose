package ru.cityron.data.room.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "events")
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = Table.ID,
    val date: String,
    val time: String,
    val type: Int,
    val text: String,
)
