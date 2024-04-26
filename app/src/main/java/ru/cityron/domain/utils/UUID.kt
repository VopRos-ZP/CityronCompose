package ru.cityron.domain.utils

import java.util.UUID

object UUID {

    fun generate(): String = UUID.randomUUID().toString()

}