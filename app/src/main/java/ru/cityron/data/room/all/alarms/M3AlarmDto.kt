package ru.cityron.data.room.all.alarms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m3_alarms")
data class M3AlarmDto(
    @PrimaryKey
    val id: Int,
    val en: Int = 0,
    val delay: Int = 0,
    @ColumnInfo("delay_min")
    val delayMin: Int = 0,
    @ColumnInfo("delay_max")
    val delayMax: Int = 0,
    val value: Int = 0,
    @ColumnInfo("value_min")
    val valueMin: Int = 0,
    @ColumnInfo("value_max")
    val valueMax: Int = 0,
    val action: Int = 0,
)
