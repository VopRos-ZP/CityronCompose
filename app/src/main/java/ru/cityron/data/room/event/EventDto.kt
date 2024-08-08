package ru.cityron.data.room.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val count: Int,
    val types: Int,
    val reasons: Int,
    val sources: Int,
)
