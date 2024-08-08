package ru.cityron.data.room.event

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [EventDto::class]
)
abstract class EventDatabase : RoomDatabase() {
    abstract val dao: EventDao
}