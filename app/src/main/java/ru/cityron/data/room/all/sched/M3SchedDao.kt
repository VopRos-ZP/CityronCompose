package ru.cityron.data.room.all.sched

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface M3SchedDao {

    @Query("SELECT * FROM m3_sched")
    fun listenM3SchedList(): Flow<List<M3SchedDto>>

    @Query("SELECT * FROM m3_sched WHERE id = :id")
    suspend fun fetchM3Sched(id: Int): M3SchedDto

    @Upsert
    suspend fun upsertM3Sched(dto: M3SchedDto)

}