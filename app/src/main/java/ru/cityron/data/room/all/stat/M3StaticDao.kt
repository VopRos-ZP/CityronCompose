package ru.cityron.data.room.all.stat

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3StaticDao {

    @Query("SELECT * FROM m3_static WHERE id = :id")
    fun listenM3Static(id: Int = Table.ID): Flow<M3StaticDto>

    @Query("SELECT * FROM m3_static WHERE id = :id")
    suspend fun fetchM3Static(id: Int = Table.ID): M3StaticDto

    @Upsert
    suspend fun upsertM3Static(dto: M3StaticDto)

}