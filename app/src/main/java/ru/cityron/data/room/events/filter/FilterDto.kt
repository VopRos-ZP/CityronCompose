package ru.cityron.data.room.events.filter

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "filters")
data class FilterDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = Table.ID,
    val count: Int,
    val types: Int,
    val reasons: Int,
    val sources: Int,
)
