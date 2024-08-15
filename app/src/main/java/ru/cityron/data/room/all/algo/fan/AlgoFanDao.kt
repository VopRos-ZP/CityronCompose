package ru.cityron.data.room.all.algo.fan

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoFanDao {

    @Query("SELECT * FROM algo_fans WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoFanDto>

    @Query("SELECT * FROM algo_fans WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoFanDto

    @Upsert
    suspend fun upsert(dto: AlgoFanDto)

}