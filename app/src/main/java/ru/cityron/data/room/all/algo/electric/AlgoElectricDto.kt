package ru.cityron.data.room.all.algo.electric

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_electrics")
data class AlgoElectricDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val heatPwmPeriod: Int,
    val heatPwmPeriodMin: Int,
    val heatPwmPeriodMax: Int,
)
