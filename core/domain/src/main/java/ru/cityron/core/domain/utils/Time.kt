package ru.cityron.core.domain.utils

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

object Time {

    private const val time = "HH:mm:ss"
    private const val date = "dd.MM.yyyy"
    private const val datetime = "$date $time"

    val formatTime = DateTimeFormatter.ofPattern(time)
    val formatDate = DateTimeFormatter.ofPattern(date)
    val formatDatetime = DateTimeFormatter.ofPattern(datetime)

    fun secondsToString(seconds: Int, zone: Int, formatter: DateTimeFormatter): String {
        val datetime = Instant.ofEpochSecond(seconds.toLong())
            .atZone(TimeZone.getTimeZone("GMT$zone").toZoneId())
            .toLocalDateTime()
        return datetime.format(formatter)
    }

    fun secondsGMT(seconds: Int, formatter: DateTimeFormatter): String {
        val date = Date(seconds.toLong())
        return date.toInstant().atZone(ZoneOffset.UTC).format(formatter)
    }

}