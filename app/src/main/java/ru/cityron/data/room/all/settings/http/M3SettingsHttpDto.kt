package ru.cityron.data.room.all.settings.http

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_settings_http")
data class M3SettingsHttpDto(
    @PrimaryKey
    val id: Int = Table.ID,
    @ColumnInfo(name = "f_pr")
    val fPr: Int = 0,
    @ColumnInfo(name = "f_pu")
    val fPu: Int = 0,
    @ColumnInfo(name = "f_pw")
    val fPw: Int = 0,
    val pr: String = "",
    val pu: String = "",
    val pw: String = "",
)
