package ru.cityron.data.room.all.algo.pi

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoPiDao {

    @Query("SELECT * FROM algo_pies WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoPiDto>

    @Query("SELECT * FROM algo_pies WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoPiDto

    @Upsert
    suspend fun upsert(dto: AlgoPiDto)

}