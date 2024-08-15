package ru.cityron.data.room.all.algo.electric

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface AlgoElectricDao {

    @Query("SELECT * FROM algo_electrics WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<AlgoElectricDto>

    @Query("SELECT * FROM algo_electrics WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): AlgoElectricDto

    @Upsert
    suspend fun upsert(dto: AlgoElectricDto)

}