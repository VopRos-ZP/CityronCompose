package ru.cityron.data.room.ip

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface IpDao {

    @Query("SELECT * FROM ips")
    suspend fun fetchAll(): List<IpDto>

    @Upsert
    suspend fun upsert(ip: IpDto)

    @Delete
    suspend fun delete(ip: IpDto)

}