package com.vopros.cityron.utils

object Temp {

    fun toGrade(temp: Int): String = temp.toString().toMutableList().let {
        it.add(2, '.')
        it.joinToString(separator = "")
    }

}