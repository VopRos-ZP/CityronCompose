package ru.cityron.data.room.all.sched

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_sched")
data class M3SchedDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val day: Int = 0,
    val fan: Int = 1,
    val hour: Int = 0,
    val min: Int = 0,
    val mode: Int = 0,
    val on: Int = 0,
    val power: Int = 0,
    val temp: Int = 22
)
