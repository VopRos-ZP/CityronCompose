package ru.cityron.domain.utils

import kotlinx.serialization.encodeToString
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

fun Int.fromIndexToFrequency(): Int = when (this) {
    1 -> 3
    2 -> 6
    3 -> 6 * 5
    4 -> 6 * 10
    5 -> 6 * 30
    else -> 1
}

fun Int.toControllerNum(): String = if (this < 10) "0$this" else "$this"

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

inline fun <reified T> toJson(t: T): String = Json.encodeToString(t)