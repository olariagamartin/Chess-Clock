package com.themarto.chessclock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chess_clock")
data class ChessClock(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo
    var firstPlayerTime: Long,
    @ColumnInfo
    var secondPlayerTime: Long,
    @ColumnInfo
    var thumbnail: Int,
    @ColumnInfo
    var gameType: String
)