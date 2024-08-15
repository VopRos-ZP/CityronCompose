package ru.cityron.data.room.all.settings.eth

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.cityron.data.room.all.Table

@Dao
interface M3SettingsEthDao {

    @Query("SELECT * FROM m3_settings_eth WHERE id = :id")
    fun listenSettingsEth(id: Int = Table.ID): Flow<M3SettingsEthDto>

    @Query("SELECT * FROM m3_settings_eth WHERE id = :id")
    suspend fun fetchSettingsEth(id: Int = Table.ID): M3SettingsEthDto

    @Upsert
    suspend fun upsertSettingsEth(dto: M3SettingsEthDto)

}