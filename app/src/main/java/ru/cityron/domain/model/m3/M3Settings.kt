package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable
import ru.cityron.domain.model.*

@Serializable
data class M3Settings(
    val alarm1: Alarm = Alarm(),
    val alarm2: Alarm = Alarm(),
    val alarm3: Alarm = Alarm(),
    val alarm4: Alarm = Alarm(),
    val alarm5: Alarm = Alarm(),
    val alarm6: Alarm = Alarm(),
    val alarm7: Alarm = Alarm(),
    val alarm8: Alarm = Alarm(),
    val alarm9: Alarm = Alarm(),
    val algo: M3Algo = M3Algo(),
    val eth: Eth = Eth(),
    val http: Http = Http(),
    val mb: Mb = Mb(),
    val metric: Metric = Metric(),
    val others: Others = Others(),
    val srv: Srv = Srv(),
    val time: Time = Time()
)