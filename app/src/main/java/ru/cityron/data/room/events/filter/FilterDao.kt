package ru.cityron.data.room.events.filter

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface FilterDao {

    @Query("SELECT * FROM filters WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<FilterDto>

    @Query("SELECT * FROM filters WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): FilterDto

    @Upsert
    suspend fun upsert(dto: FilterDto)

}