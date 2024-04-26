package ru.cityron.domain.utils

object Temp {

    fun toGrade(temp: Int): String = temp.toString().toMutableList().let {
        when (it.size) {
            2 -> it.add(1, '.')
            3 -> it.add(2, '.')
            else -> Unit
        }
        it.joinToString(separator = "")
    }

}