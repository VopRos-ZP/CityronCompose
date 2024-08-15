package ru.cityron.data.room.all.algo.timings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoTimingDao {

    @Query("SELECT * FROM algo_timings WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoTimingDto>

    @Query("SELECT * FROM algo_timings WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoTimingDto

    @Upsert
    suspend fun upsert(dto: AlgoTimingDto)

}