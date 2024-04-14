package ru.cityron.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ControllerEntity::class],
    version = 1
)
abstract class ControllerDatabase : RoomDatabase() {
    abstract val dao: ControllerDao
}