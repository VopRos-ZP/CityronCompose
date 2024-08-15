package ru.cityron.data.room.all.settings.other

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3SettingsOtherDao {

    @Query("SELECT * FROM m3_settings_others  WHERE id = :id")
    fun listen(id: Int = Table.ID): Flow<M3SettingsOtherDto>

    @Query("SELECT * FROM m3_settings_others  WHERE id = :id")
    suspend fun fetch(id: Int = Table.ID): M3SettingsOtherDto

    @Upsert
    suspend fun upsert(dto: M3SettingsOtherDto)

}