package ru.cityron.domain.utils

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object Time {

    private const val TIME = "HH:mm:ss"
    private const val DATE = "dd.MM.yyyy"
    private const val DATETIME = "$DATE $TIME"

    val formatDatetime: DateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME)

    fun secondsToString(seconds: Long, zone: Int, formatter: DateTimeFormatter): String {
        val datetime = Instant.ofEpochSecond(seconds)
            .atZone(TimeZone.getTimeZone("GMT${if (zone >= 0) "+" else ""}$zone").toZoneId())
            .toLocalDateTime()
        return datetime.format(formatter)
    }

}