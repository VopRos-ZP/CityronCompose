package ru.cityron.domain.utils

object Path {

    const val CONF = "conf"

    const val JSON_ALL = "json?all"
    const val JSON_STATE = "json?state"
    const val JSON_STATIC = "json?static"
    const val JSON_SETTINGS = "json?settings"
    const val JSON_SCHED = "json?sched"
    const val JSON_CHART = "json?chart"
    const val JSON_EVENTS = "json?events"

    private const val JOIN_PARAM_SEPARATOR = "&"

    fun List<Any>.toQueryParams(): String = joinToString(separator = JOIN_PARAM_SEPARATOR) { "$it" }

    fun Map<String, Any>.toParameters(): String = map { (k, v) -> "$k=$v" }.joinToString(separator = JOIN_PARAM_SEPARATOR)

}