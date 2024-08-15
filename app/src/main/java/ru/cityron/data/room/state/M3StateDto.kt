package ru.cityron.data.room.state

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state")
data class M3StateDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alarms: Int = 0,
    val modeZimaLeto: Int = 0,
    val status: Int = 0,
    val statusSec: Int = 0,
    val tempBWater: Int = 0,
    val tempExt: Int = 50,
    val tempPv: Int = 50,
    val type: Int = 0,
    val crcSched: String = "",
    val crcSettings: String = "",
    val ipLoc: String = "",
    val rtcTime: Long = 0,
    val fan: Int = 1,
    val mode: Int = 0,
    val power: Int = 0,
    val sched: Int = 0,
    val temp: Int = 50,
    val connect: Int = 0,
    val isBind: Int = 0,
    val metricAddr: String = "",
    val ul: Int = 0,
    val uptime: Long = 0
)
