package ru.cityron.data.room.all.alarms

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [M3AlarmDto::class],
    version = 1
)
abstract class M3AlarmsDatabase : RoomDatabase() {
    abstract val dao: M3AlarmDao

    companion object {
        const val NAME = "alarms"
    }
}