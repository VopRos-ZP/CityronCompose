package ru.cityron.data.room.all.settings.metric

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_settings_metric")
data class M3SettingsMetricDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val frequency: Int = 1,
    @ColumnInfo(name = "frequency_min")
    val frequencyMin: Int = 1,
    @ColumnInfo(name = "frequency_max")
    val frequencyMax: Int = 7,
    @ColumnInfo(name = "values_bits")
    val valuesBits: Int = 0,
    @ColumnInfo(name = "values_bits_min")
    val valuesBitsMin: Int = 0,
    @ColumnInfo(name = "values_bits_max")
    val valuesBitsMax: Int = 255,
)
