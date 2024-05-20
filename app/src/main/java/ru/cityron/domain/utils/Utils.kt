package ru.cityron.domain.utils


fun Boolean.toInt() = if (this) 1 else 0

fun utilsBitGet(num: Int, bit: Int): Boolean = num.and(1.shl(bit)) != 0