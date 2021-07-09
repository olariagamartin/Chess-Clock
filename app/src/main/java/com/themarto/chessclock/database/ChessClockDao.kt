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
    fun count(): Int

    @Insert
    fun insert(chessClock: ChessClock)

    @Query("SELECT * FROM chess_clock WHERE id=:id")
    fun get(id: Long): ChessClock

    @Delete
    fun delete(clock: ChessClock)
}