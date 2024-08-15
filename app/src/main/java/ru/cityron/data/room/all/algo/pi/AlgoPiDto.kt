package ru.cityron.data.room.all.algo.pi

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "algo_pies")
data class AlgoPiDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val piAutoEn: Int? = null,

    val piKofP: Int = 33,
    val piKofPMin: Int = 0,
    val piKofPMax: Int = 255,

    val piKofI: Int = 100,
    val piKofIMin: Int = 0,
    val piKofIMax: Int = 255,

    val piErr: Int = 0,
    val piErrMin: Int = 0,
    val piErrMax: Int = 255,
)
