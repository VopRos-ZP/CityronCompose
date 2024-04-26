package ru.cityron.domain.utils

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

    fun secondsToString(seconds: Long, zone: Int, formatter: DateTimeFormatter): String {
        val datetime = Instant.ofEpochSecond(seconds)
            .atZone(TimeZone.getTimeZone("GMT$zone").toZoneId())
            .toLocalDateTime()
        return datetime.format(formatter)
    }

    fun secondsGMT(seconds: Long, formatter: DateTimeFormatter): String {
        val date = Date(seconds)
        return date.toInstant().atZone(ZoneOffset.UTC).format(formatter)
    }

}