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
    suspend fun insert(chessClock: ChessClock)

    @Insert
    suspend fun insertAll(chessClockList: List<ChessClock>)

    @Query("SELECT * FROM chess_clock WHERE id=:id")
    suspend fun get(id: Long): ChessClock

    @Delete
    suspend fun delete(clock: ChessClock)
}