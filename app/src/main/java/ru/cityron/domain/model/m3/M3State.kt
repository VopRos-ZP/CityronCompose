package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable
import ru.cityron.domain.model.Set
import ru.cityron.domain.model.SrvMin

/**
 * @param alarms количество тревог
 * @param algo Алгоритм
 * @param crcSched
 * @param crcSettings
 * @param ipLoc Локальный IP адресс
 * @param rtcTime Время в секундах
 * @param set
 * @param srv
 * @param ul
 * @param uptime Время работы с момента включения
 * **/
@Serializable
data class M3State(
    val alarms: Int = 0,
    val algo: M3AlgoMin = M3AlgoMin(),
    val crcSched: String = "",
    val crcSettings: String = "",
    val ipLoc: String = "",
    val rtcTime: Long = 0,
    val set: Set = Set(),
    val srv: SrvMin = SrvMin(),
    val ul: Int = 0,
    val uptime: Long = 0
)