package ru.cityron.data.room.all.settings

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.cityron.data.room.all.settings.eth.M3SettingsEthDao
import ru.cityron.data.room.all.settings.eth.M3SettingsEthDto
import ru.cityron.data.room.all.settings.http.M3SettingsHttpDao
import ru.cityron.data.room.all.settings.http.M3SettingsHttpDto
import ru.cityron.data.room.all.settings.metric.M3SettingsMetricDao
import ru.cityron.data.room.all.settings.metric.M3SettingsMetricDto
import ru.cityron.data.room.all.settings.other.M3SettingsOtherDao
import ru.cityron.data.room.all.settings.other.M3SettingsOtherDto
import ru.cityron.data.room.all.settings.time.M3SettingsTimeDao
import ru.cityron.data.room.all.settings.time.M3SettingsTimeDto

@Database(
    entities = [
        M3SettingsTimeDto::class,
        M3SettingsEthDto::class,
        M3SettingsHttpDto::class,
        M3SettingsMetricDto::class,
        M3SettingsOtherDto::class
    ],
    version = 3
)
abstract class M3SettingsDatabase : RoomDatabase() {
    abstract val timeDao: M3SettingsTimeDao
    abstract val ethDao: M3SettingsEthDao
    abstract val httpDao: M3SettingsHttpDao
    abstract val metricDao: M3SettingsMetricDao
    abstract val otherDao: M3SettingsOtherDao

    companion object {
        const val NAME = "settings"
    }
}