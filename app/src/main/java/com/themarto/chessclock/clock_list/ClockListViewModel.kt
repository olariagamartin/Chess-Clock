package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.ViewModel
import com.themarto.chessclock.database.ChessClockDatabase

class ClockListViewModel(application: Application): ViewModel() {
    private val database = ChessClockDatabase.getInstance(application).chessClockDao
}