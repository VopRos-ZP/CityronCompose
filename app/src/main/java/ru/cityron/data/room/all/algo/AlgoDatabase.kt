package ru.cityron.data.room.all.algo

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.cityron.data.room.all.algo.electric.*
import ru.cityron.data.room.all.algo.fan.*
import ru.cityron.data.room.all.algo.other.*
import ru.cityron.data.room.all.algo.pi.*
import ru.cityron.data.room.all.algo.timings.*
import ru.cityron.data.room.all.algo.water.*

@Database(
    entities = [
        AlgoElectricDto::class,
        AlgoFanDto::class,
        AlgoOtherDto::class,
        AlgoPiDto::class,
        AlgoTimingDto::class,
        AlgoWaterDto::class
    ],
    version = 1
)
abstract class AlgoDatabase : RoomDatabase() {
    abstract val electricDao: AlgoElectricDao
    abstract val fanDao: AlgoFanDao
    abstract val otherDao: AlgoOtherDao
    abstract val piDao: AlgoPiDao
    abstract val timingDao: AlgoTimingDao
    abstract val waterDao: AlgoWaterDao

    companion object {
        const val NAME = "algo"
    }
}