package ru.cityron.domain.utils

object Path {

    const val CONF = "conf"

    const val JSON_ALL = "json?all"
    const val JSON_STATE = "json?state"
    const val JSON_CHART = "json?chart"
    const val JSON_EVENTS = "json?events"

    fun Map<String, Any>.toParameters(): String = map { (k, v) -> "$k=$v" }
        .joinToString(separator = "&")

}