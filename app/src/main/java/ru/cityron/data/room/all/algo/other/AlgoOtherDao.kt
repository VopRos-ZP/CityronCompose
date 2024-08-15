package ru.cityron.data.room.all.algo.other

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoOtherDao {

    @Query("SELECT * FROM algo_others WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoOtherDto>

    @Query("SELECT * FROM algo_others WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoOtherDto

    @Upsert
    suspend fun upsert(dto: AlgoOtherDto)

}