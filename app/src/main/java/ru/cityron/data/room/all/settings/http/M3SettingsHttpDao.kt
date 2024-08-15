package ru.cityron.data.room.all.settings.http

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3SettingsHttpDao {

    @Query("SELECT * FROM m3_settings_http WHERE id = :id")
    fun listenSettingsHttp(id: Int = Table.ID): Flow<M3SettingsHttpDto>

    @Query("SELECT * FROM m3_settings_http WHERE id = :id")
    suspend fun fetchSettingsHttp(id: Int = Table.ID): M3SettingsHttpDto

    @Upsert
    suspend fun upsertSettingsHttp(dto: M3SettingsHttpDto)

}