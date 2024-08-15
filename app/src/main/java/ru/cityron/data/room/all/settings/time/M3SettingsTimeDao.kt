package ru.cityron.data.room.all.settings.time

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3SettingsTimeDao {

    @Query("SELECT * FROM m3_settings_time WHERE id = :id")
    fun listenSettingsTime(id: Int = Table.ID): Flow<M3SettingsTimeDto>

    @Query("SELECT * FROM m3_settings_time WHERE id = :id")
    suspend fun fetchSettingsTime(id: Int = Table.ID): M3SettingsTimeDto

    @Upsert
    suspend fun upsertSettingsTime(dto: M3SettingsTimeDto)

}