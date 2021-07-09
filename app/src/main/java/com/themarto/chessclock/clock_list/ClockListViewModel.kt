package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.ViewModel
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListViewModel(application: Application): ViewModel() {
    private val database = ChessClockDatabase.getInstance(application).chessClockDao

    fun getDummyData(): List<ChessClock>{
        return arrayListOf(
            ChessClock(0, ONE_MINUTE, ONE_MINUTE),
            ChessClock(1, 2*ONE_MINUTE),
            ChessClock(2, 5*ONE_MINUTE),
            ChessClock(3, 10*ONE_MINUTE),
            ChessClock(4, 15*ONE_MINUTE),
            ChessClock(5, 60*ONE_MINUTE),
        )
    }
}