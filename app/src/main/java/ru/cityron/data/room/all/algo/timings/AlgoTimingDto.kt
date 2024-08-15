package ru.cityron.data.room.all.algo.timings

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_timings")
data class AlgoTimingDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val timeOpenDamper: Int = 30,
    val timeOpenDamperMin: Int = 5,
    val timeOpenDamperMax: Int = 90,

    val timeAccelerFan: Int = 10,
    val timeAccelerFanMin: Int = 1,
    val timeAccelerFanMax: Int = 60,

    val timeBlowHeat: Int = 10,
    val timeBlowHeatMin: Int = 1,
    val timeBlowHeatMax: Int = 60,
)
