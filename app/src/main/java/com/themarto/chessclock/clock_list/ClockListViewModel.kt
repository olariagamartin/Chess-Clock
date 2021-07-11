package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListViewModel(application: Application): ViewModel() {
    private val database = ChessClockDatabase
        .getInstance(application, viewModelScope).chessClockDao

    val clocks = database.getAllChessClocks()

    private val _currentClockId = MutableLiveData<Long>()
    val currentClockId: LiveData<Long>
        get() = _currentClockId

    fun setCurrentClockId(id: Long) {
        _currentClockId.value = id
    }

}