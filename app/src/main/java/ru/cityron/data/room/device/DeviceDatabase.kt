package ru.cityron.data.room.device

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DeviceDto::class],
    version = 1
)
abstract class DeviceDatabase : RoomDatabase() {
    abstract val dao: DeviceDao

    companion object {
        const val NAME = "device"
    }
}