package ru.cityron.data.room.all.algo.water

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoWaterDao {

    @Query("SELECT * FROM algo_waters WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoWaterDto>

    @Query("SELECT * FROM algo_waters WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoWaterDto

    @Upsert
    suspend fun upsert(dto: AlgoWaterDto)

}