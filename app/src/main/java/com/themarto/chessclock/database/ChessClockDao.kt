package com.themarto.chessclock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChessClockDao {

    @Query("SELECT * FROM chess_clock")
    fun getAllChessClocks(): LiveData<List<ChessClock>>

    @Query("SELECT COUNT(*) FROM chess_clock")
    suspend fun count(): Int

    @Insert
    suspend fun insert(vararg chessClocks: ChessClock)

    @Query("SELECT * FROM chess_clock WHERE id=:id")
    suspend fun get(id: Long): ChessClock

    @Delete
    suspend fun delete(clock: ChessClock)
}