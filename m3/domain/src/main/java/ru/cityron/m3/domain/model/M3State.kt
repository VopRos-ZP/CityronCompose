package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable

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
    val alarms: Int,
    val algo: M3AlgoMin,
    val crcSched: String,
    val crcSettings: String,
    val ipLoc: String,
    val rtcTime: Int,
    val set: M3SetMin,
    val srv: M3SrvMin,
    val ul: Int,
    val uptime: Int
)