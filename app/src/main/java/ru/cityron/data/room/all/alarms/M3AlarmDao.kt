package ru.cityron.data.room.all.alarms

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface M3AlarmDao {

    @Query("SELECT * FROM m3_alarms")
    fun listenM3Alarms(): Flow<List<M3AlarmDto>>

    @Query("SELECT * FROM m3_alarms WHERE id = :id")
    suspend fun fetchM3Alarm(id: Int): M3AlarmDto

    @Upsert
    suspend fun upsertM3Alarm(dto: M3AlarmDto)

}