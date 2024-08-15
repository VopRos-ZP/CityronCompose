package ru.cityron.data.room.controller

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 6,
    entities = [ControllerDto::class]
)
abstract class ControllerDatabase : RoomDatabase() {
    abstract val dao: ControllerDao

    companion object {
        const val NAME = "controllers"
    }
}