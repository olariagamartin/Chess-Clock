package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListViewModel(application: Application): ViewModel() {
    private val database = ChessClockDatabase
        .getInstance(application, viewModelScope).chessClockDao

    val clocks = database.getAllChessClocks()

}