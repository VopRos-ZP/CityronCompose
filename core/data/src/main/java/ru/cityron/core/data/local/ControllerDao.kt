package ru.cityron.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ControllerDao {

    @Query("SELECT * FROM controller")
    suspend fun fetchAllController(): List<ControllerEntity>

    @Upsert
    suspend fun upsertController(controller: ControllerEntity)

    @Query("SELECT * FROM controller WHERE id = :id")
    suspend fun fetchControllerById(id: Int): ControllerEntity

    @Delete
    suspend fun deleteController(controller: ControllerEntity)

}
