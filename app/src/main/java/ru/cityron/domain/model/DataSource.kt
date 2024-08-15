package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class DataSource {
    LOCAL, REMOTE;
}

@Serializable
sealed class Status(open val source: DataSource) {
    @Serializable
    data class Online(private val ds: DataSource) : Status(ds)
    @Serializable
    data class Alert(private val db: DataSource) : Status(db)
    @Serializable
    data object Offline : Status(DataSource.LOCAL)
}