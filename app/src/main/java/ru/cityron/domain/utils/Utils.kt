package ru.cityron.domain.utils

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