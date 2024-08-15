package ru.cityron.data.room.all.settings.metric

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3SettingsMetricDao {

    @Query("SELECT * FROM m3_settings_metric WHERE id = :id")
    fun listenSettingsMetric(id: Int = Table.ID): Flow<M3SettingsMetricDto>

    @Query("SELECT * FROM m3_settings_metric WHERE id = :id")
    suspend fun fetchSettingsMetric(id: Int = Table.ID): M3SettingsMetricDto

    @Upsert
    suspend fun upsertSettingsMetric(dto: M3SettingsMetricDto)

}