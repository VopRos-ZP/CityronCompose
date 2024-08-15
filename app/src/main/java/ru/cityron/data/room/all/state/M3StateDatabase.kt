package ru.cityron.data.room.all.state

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [M3StateDto::class],
    version = 2
)
abstract class M3StateDatabase : RoomDatabase() {
    abstract val dao: M3StateDao

    companion object {
        const val NAME = "state"
    }
}