package com.themarto.chessclock.create_edit_clock

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.ChessUtils
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_SECOND
import kotlinx.coroutines.launch

class CreateEditViewModel(
    application: Application,
    private val clockId: Long,
    private val editOption: Boolean
) : ViewModel() {

    private val database = ChessClockDatabase.getInstance(application, viewModelScope)
        .chessClockDao

    private val _chessClock = MutableLiveData<ChessClock>()
    private val chessClock: LiveData<ChessClock> get() = _chessClock

    val firstPlayerTime: LiveData<String> = Transformations.map(chessClock) {
        DateUtils.formatElapsedTime(it.firstPlayerTime / ONE_SECOND)
    }
    // todo: add increment
    val secondPlayerTime: LiveData<String> = Transformations.map(chessClock) {
        DateUtils.formatElapsedTime(it.secondPlayerTime / ONE_SECOND)
    }

    init {
        loadClock()
    }

    private fun loadClock() {
        if (editOption) {
            viewModelScope.launch {
                _chessClock.value = database.get(clockId)
            }
        } else {
            _chessClock.value = getDefaultClock()
        }
    }

    private fun getDefaultClock(): ChessClock {
        return ChessClock(firstPlayerTime = 5*ONE_MINUTE, secondPlayerTime = 5* ONE_MINUTE)
    }
}