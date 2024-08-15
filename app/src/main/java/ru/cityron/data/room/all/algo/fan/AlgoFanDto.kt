package ru.cityron.data.room.all.algo.fan

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_fans")
data class AlgoFanDto(
    @PrimaryKey
    val id: Int = Table.ID,

    val fanSpeedMin: Int = 20,
    val fanSpeedMinMin: Int = 1,
    val fanSpeedMinMax: Int = 90,

    val fanSpeedMax: Int = 100,
    val fanSpeedMaxMin: Int = 20,
    val fanSpeedMaxMax: Int = 100,
)
