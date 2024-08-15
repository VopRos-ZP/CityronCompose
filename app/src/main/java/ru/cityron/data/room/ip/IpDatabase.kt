package ru.cityron.data.room.ip

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [IpDto::class],
    version = 1
)
abstract class IpDatabase : RoomDatabase() {
    abstract val dao: IpDao

    companion object {
        const val NAME = "ip"
    }
}