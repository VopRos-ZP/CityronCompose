package ru.cityron.data.room.all.stat

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [M3StaticDto::class],
    version = 1
)
abstract class M3StaticDatabase : RoomDatabase() {
    abstract val dao: M3StaticDao

    companion object {
        const val NAME = "static"
    }
}