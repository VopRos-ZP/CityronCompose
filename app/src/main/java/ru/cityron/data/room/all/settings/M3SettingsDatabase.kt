package ru.cityron.data.room.all.settings

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.cityron.data.room.all.settings.eth.*
import ru.cityron.data.room.all.settings.http.*
import ru.cityron.data.room.all.settings.metric.*
import ru.cityron.data.room.all.settings.time.*

@Database(
    entities = [
        M3SettingsTimeDto::class,
        M3SettingsEthDto::class,
        M3SettingsHttpDto::class,
        M3SettingsMetricDto::class
    ],
    version = 2
)
abstract class M3SettingsDatabase : RoomDatabase() {
    abstract val timeDao: M3SettingsTimeDao
    abstract val ethDao: M3SettingsEthDao
    abstract val httpDao: M3SettingsHttpDao
    abstract val metricDao: M3SettingsMetricDao

    companion object {
        const val NAME = "settings"
    }
}