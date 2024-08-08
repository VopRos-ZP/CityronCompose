package ru.cityron.data.room.event

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.cityron.data.room.controller.ControllerDto

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    suspend fun fetchAll(): List<EventDto>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun fetchById(id: Int): EventDto

    @Upsert
    suspend fun upsert(event: EventDto)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun delete(id: Int)

}