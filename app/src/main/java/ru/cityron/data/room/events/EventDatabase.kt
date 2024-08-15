package ru.cityron.data.room.events

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.cityron.data.room.events.filter.FilterDao
import ru.cityron.data.room.events.filter.FilterDto

@Database(
    entities = [
        FilterDto::class,
        EventDto::class
    ],
    version = 1
)
abstract class EventDatabase : RoomDatabase() {
    abstract val filterDao: FilterDao
    abstract val eventDao: EventDao

    companion object {
        const val NAME = "events"
    }
}