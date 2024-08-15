package ru.cityron.data.room.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun listen(): Flow<List<EventDto>>

    @Insert
    suspend fun insert(dto: EventDto)

    @Query("DELETE FROM events")
    suspend fun deleteAll()

}