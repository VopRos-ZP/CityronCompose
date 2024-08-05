package ru.cityron.domain.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object Time {

    private const val TIME = "HH:mm:ss"
    private const val DATE = "dd.MM.yyyy"
    private const val DATETIME = "$DATE $TIME"

    val formatDatetime: DateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME)

    fun secondsToString(seconds: Long, zone: Int, formatter: DateTimeFormatter): String {
        val datetime = Instant.ofEpochSecond(seconds)
            .atZone(getZoneId(zone))
            .toLocalDateTime()
        return datetime.format(formatter)
    }

    fun stringToSeconds(str: String, zone: Int, formatter: DateTimeFormatter): Int {
        return LocalDateTime
            .parse(str, formatter)
            .atZone(getZoneId(zone))
            .second
    }

    private fun getZoneId(zone: Int): ZoneId = TimeZone.getTimeZone("GMT${zone.toTimeZone()}").toZoneId()

}