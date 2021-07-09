package com.themarto.chessclock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.themarto.chessclock.utils.ChessUtils.Companion.BLITZ
import com.themarto.chessclock.utils.ChessUtils.Companion.BULLET
import com.themarto.chessclock.utils.ChessUtils.Companion.CLASSIC
import com.themarto.chessclock.utils.ChessUtils.Companion.RAPID
import kotlin.math.max

@Entity(tableName = "chess_clock")
data class ChessClock(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo
    var firstPlayerTime: Long = 0L,
    @ColumnInfo
    var secondPlayerTime: Long = 0L,
    @ColumnInfo
    var gameType: Int = 0
) {
    init {
        val maxTime = max(firstPlayerTime, secondPlayerTime)
        gameType = when {
            maxTime < BULLET -> BULLET
            maxTime < BLITZ -> BLITZ
            maxTime < RAPID -> RAPID
            else -> CLASSIC
        }
    }
}