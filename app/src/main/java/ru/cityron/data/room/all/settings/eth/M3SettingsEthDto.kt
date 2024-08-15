package ru.cityron.data.room.all.settings.eth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_settings_eth")
data class M3SettingsEthDto(
    @PrimaryKey
    val id: Int = Table.ID,
    @ColumnInfo("f_dhcp")
    val fDhcp: Int = 0,
    val gw: String = "",
    val ip: String = "",
    val mac2: String = "",
    val mask: String = "",
)
