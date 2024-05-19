package ru.cityron.data.room.controller

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ControllerDao {

    @Query("SELECT * FROM controllers")
    suspend fun fetchAll(): List<ControllerDto>

    @Upsert
    suspend fun upsert(controller: ControllerDto)

    @Query("DELETE FROM controllers WHERE id = :id")
    suspend fun delete(id: Int)

}