package ru.cityron.data.room.device

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.cityron.data.room.all.Table

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): DeviceDto

    @Upsert
    suspend fun upsert(dto: DeviceDto)

}