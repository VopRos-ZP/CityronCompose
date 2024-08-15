package ru.cityron.data.room.all.state

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3StateDao {

    @Query("SELECT * FROM m3_state WHERE id = :id")
    fun listenM3State(id: Int = Table.ID): Flow<M3StateDto>

    @Query("SELECT * FROM m3_state WHERE id = :id")
    suspend fun fetchM3State(id: Int = Table.ID): M3StateDto

    @Upsert
    suspend fun upsertM3State(dto: M3StateDto)

}