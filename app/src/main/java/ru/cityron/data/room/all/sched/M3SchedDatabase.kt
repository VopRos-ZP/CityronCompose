package ru.cityron.data.room.all.sched

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [M3SchedDto::class],
    version = 1
)
abstract class M3SchedDatabase : RoomDatabase() {
    abstract val dao: M3SchedDao

    companion object {
        const val NAME = "sched"
    }
}