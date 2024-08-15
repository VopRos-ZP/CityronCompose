package ru.cityron.data.room.all.algo.other

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_others")
data class AlgoOtherDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val tempControl: Int = 0,
    val filterEn: Int = 1,
    val autoStartEn: Int = 1,
    val isDistPower: Int = 0,
    val alarmRestartCount: Int = 5,
    val alarmRestartCountMin: Int = 0,
    val alarmRestartCountMax: Int = 10,
)
