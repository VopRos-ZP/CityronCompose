package ru.cityron.domain.utils

fun toTime(num: Int): String = if (num < 10) "0$num" else "$num"

fun Boolean.toInt() = if (this) 1 else 0

fun utilsBitGet(num: Int, bit: Int): Boolean = num.and(1.shl(bit)) != 0