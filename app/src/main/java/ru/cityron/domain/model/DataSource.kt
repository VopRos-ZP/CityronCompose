package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class DataSource(open val status: Status) {
    data class Local(override val status: Status) : DataSource(status)
    data class Remote(override val status: Status) : DataSource(status)
}

@Serializable
enum class Status {
    ONLINE, ALERT, OFFLINE;
}