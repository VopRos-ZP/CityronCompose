package ru.cityron.data.room.all.settings.other

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_settings_others")
data class M3SettingsOtherDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val fDebug: Int,
    val fTest: Int,
    val loc: String,
    val ntcFilter: String
)
