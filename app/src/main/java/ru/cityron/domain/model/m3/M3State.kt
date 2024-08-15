package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable
import ru.cityron.domain.model.Set
import ru.cityron.domain.model.SrvMin

/**
 * @param alarms количество тревог
 * @param algo Алгоритм
 * @param crcSched
 * @param crcBinds
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
    val rtcTime: Long = 0,
    val uptime: Long = 0,
    val ul: Int = 0,
    val ipLoc: String = "",
    val crcSettings: String = "",
    val crcSched: String = "",
    val crcBinds: String = "",
    val srv: SrvMin = SrvMin(),
    val algo: M3AlgoMin = M3AlgoMin(),
    val set: Set = Set(),
)