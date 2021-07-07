package com.themarto.chessclock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chess_clock")
data class ChessClock(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo
    val firstPlayerTime: Long,
    @ColumnInfo
    val secondPlayerTime: Long,
    @ColumnInfo
    val thumbnail: Int,
    @ColumnInfo
    val gameType: String
)