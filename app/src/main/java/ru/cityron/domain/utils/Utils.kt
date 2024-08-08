package ru.cityron.domain.utils

import kotlinx.serialization.json.Json

fun toTime(num: Int): String = if (num < 10) "0$num" else "$num"

fun Boolean.toInt() = if (this) 1 else 0

fun utilsBitGet(num: Int, bit: Int): Boolean = num.and(1.shl(bit)) != 0

fun roundToFive(num: Double): Int {
    val mod = num.mod(10.0)
    val result = (num - mod).div(10.0) * 10
    return when (mod) {
        in (0.0..2.4) -> result
        else -> result + 5
    }.toInt()
}

fun Int.toTimeZone(): String = "${if (this > 0) "+" else ""}$this"

fun Int.fromFrequencyToIndex(): Int = when (this) {
    3 -> 1
    6 -> 2
    6 * 5 -> 3
    6 * 10 -> 4
    6 * 30 -> 5
    else -> 0
}

inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)