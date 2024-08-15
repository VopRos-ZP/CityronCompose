package ru.cityron.data.room.controller

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface ControllerDao {

    @Query("SELECT * FROM controllers")
    fun listenAll(): Flow<List<ControllerDto>>

    @Query("SELECT * FROM controllers WHERE id = :id")
    fun listenOne(id: Int = Table.ID): Flow<ControllerDto>

    @Query("SELECT * FROM controllers")
    suspend fun fetchAll(): List<ControllerDto>

    @Upsert
    suspend fun upsert(controller: ControllerDto)

    @Query("DELETE FROM controllers WHERE id = :id")
    suspend fun delete(id: Int)

}