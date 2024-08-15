package ru.cityron.data.room.all.algo.water

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_waters")
data class AlgoWaterDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val modeZimaLetoSource: Int = 1,
    val modeZimaLetoUser: Int = 0,

    val timeWarmUp: Int = 3,
    val timeWarmUpMin: Int = 0,
    val timeWarmUpMax: Int = 255,

    val timeDefrost: Int = 3,
    val timeDefrostMin: Int = 0,
    val timeDefrostMax: Int = 255,
)
