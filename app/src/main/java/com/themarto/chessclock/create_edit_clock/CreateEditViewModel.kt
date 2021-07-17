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

    private var _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean> get() = _closeFragment

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
        return ChessClock(firstPlayerTime = 5 * ONE_MINUTE, secondPlayerTime = 5 * ONE_MINUTE)
    }

    fun onNavigationClick() {
        _closeFragment.value = true
    }

    fun onCloseDone() {
        _closeFragment.value = false
    }

    fun onSaveOptionMenuClick() {
        if (editOption) {
            viewModelScope.launch {
                updateClock()
                _closeFragment.value = true
            }
        } else {
            viewModelScope.launch {
                createClock()
                _closeFragment.value = true
            }
        }
    }

    private suspend fun updateClock() {
        chessClock.value?.let {
            database.update(it)
        }
    }

    private suspend fun createClock() {
        chessClock.value?.let {
            database.insert(it)
        }
    }

    fun onFirstPlayerTimeSet(hours: Int, minutes: Int, seconds: Int) {
        val timeMillis = seconds * ONE_SECOND + minutes * ONE_MINUTE + hours * 60 * ONE_MINUTE
        val clockUpdated = _chessClock.value
        clockUpdated?.firstPlayerTime = timeMillis
        _chessClock.value = clockUpdated
    }

    fun onSecondPlayerTimeSet(hours: Int, minutes: Int, seconds: Int) {
        val timeMillis = seconds * ONE_SECOND + minutes * ONE_MINUTE + hours * 60 * ONE_MINUTE
        val clockUpdated = _chessClock.value
        clockUpdated?.secondPlayerTime = timeMillis
        _chessClock.value = clockUpdated
    }

    /**
     * Return a triple where the first, second
     * and third value corresponds to the hours,
     * minutes and seconds of the first player time
     */
    fun getFirstPlayerTimeSet(): Triple<Int, Int, Int> {
        var timeSet = Triple(0, 0, 0)
        chessClock.value?.run {
            val hours = (firstPlayerTime / (ONE_MINUTE * 60)).toInt()
            val minutes = ((firstPlayerTime % (ONE_MINUTE * 60)) / ONE_MINUTE).toInt()
            val rest = (firstPlayerTime % (ONE_MINUTE * 60)) % ONE_MINUTE
            val seconds = (rest / ONE_SECOND).toInt()
            timeSet = Triple(hours, minutes, seconds)
        }
        return timeSet
    }

    /**
     * Return a triple where the first, second
     * and third value corresponds to the hours,
     * minutes and seconds of the second player time
     */
    fun getSecondPlayerTimeSet(): Triple<Int, Int, Int> {
        // todo: use time picker method
        var timeSet = Triple(0, 0, 0)
        chessClock.value?.run {
            val hours = (secondPlayerTime / (ONE_MINUTE * 60)).toInt()
            val minutes = ((secondPlayerTime % (ONE_MINUTE * 60)) / ONE_MINUTE).toInt()
            val rest = (secondPlayerTime % (ONE_MINUTE * 60)) % ONE_MINUTE
            val seconds = (rest / ONE_SECOND).toInt()
            timeSet = Triple(hours, minutes, seconds)
        }
        return timeSet
    }
}