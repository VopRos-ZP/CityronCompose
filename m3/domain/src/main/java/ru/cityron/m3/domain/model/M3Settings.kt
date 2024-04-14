package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable
import ru.cityron.core.domain.model.*

@Serializable
data class M3Settings(
    val alarm1: Alarm,
    val alarm2: Alarm,
    val alarm3: Alarm,
    val alarm4: Alarm,
    val alarm5: Alarm,
    val alarm6: Alarm,
    val alarm7: Alarm,
    val alarm8: Alarm,
    val alarm9: Alarm,
    val algo: M3Algo,
    val eth: Eth,
    val http: Http,
    val mb: Mb,
    val metric: Metric,
    val others: Others,
    val srv: Srv,
    val time: Time
)