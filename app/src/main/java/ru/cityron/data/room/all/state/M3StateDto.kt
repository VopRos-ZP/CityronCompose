package ru.cityron.data.room.all.state

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_state")
data class M3StateDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val alarms: Int = 0,
    val tempBWater: Int = 50,
    val tempExt: Int = 50,
    val tempPv: Int = 50,
    val tempPu: Int = 50,
    val fan: Int = 1,
    val power: Int = 0,
    val temp: Int = 50,
    val ipLoc: String = "",
    val rtcTime: Long = 0,
)
