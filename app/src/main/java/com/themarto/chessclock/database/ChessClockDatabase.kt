package com.themarto.chessclock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.themarto.chessclock.utils.DataUtil.Companion.getDefaultClocks

@Database(entities = [ChessClock::class], version = 1, exportSchema = false)
abstract class ChessClockDatabase: RoomDatabase() {
    abstract val chessClockDao: ChessClockDao

    companion object {
        private var INSTANCE: ChessClockDatabase? = null
        fun getInstance(context: Context): ChessClockDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        ChessClockDatabase::class.java, "chess_clock_list").build()
                    INSTANCE = instance
                    instance.prePopulateDatabase()
                }
                return instance
            }
        }
    }

    private fun prePopulateDatabase () {

    }
}