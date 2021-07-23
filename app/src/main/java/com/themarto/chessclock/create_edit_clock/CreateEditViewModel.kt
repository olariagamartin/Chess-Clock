package com.themarto.chessclock.create_edit_clock

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
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
    val chessClock: LiveData<ChessClock> get() = _chessClock

    val firstPlayerTime: LiveData<String> = Transformations.map(chessClock) {
        DateUtils.formatElapsedTime(it.firstPlayerTime / ONE_SECOND)
    }

    val secondPlayerTime: LiveData<String> = Transformations.map(chessClock) {
        DateUtils.formatElapsedTime(it.secondPlayerTime / ONE_SECOND)
    }

    val incrementTime: LiveData<String> = Transformations.map(chessClock) {
        DateUtils.formatElapsedTime(it.increment / ONE_SECOND)
    }

    private var sameValueChecked = true

    private val _sameValueSwitch = MutableLiveData<Boolean>()
    val sameValueSwitch: LiveData<Boolean> get() = _sameValueSwitch

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
        if (sameValueChecked) {
            clockUpdated?.secondPlayerTime = timeMillis
        }
        _chessClock.value = clockUpdated
    }

    fun onSecondPlayerTimeSet(hours: Int, minutes: Int, seconds: Int) {
        val timeMillis = seconds * ONE_SECOND + minutes * ONE_MINUTE + hours * 60 * ONE_MINUTE
        val clockUpdated = _chessClock.value
        clockUpdated?.secondPlayerTime = timeMillis
        if(clockUpdated?.firstPlayerTime != timeMillis) {
            _sameValueSwitch.value = false
        }
        _chessClock.value = clockUpdated
    }

    fun onSameValueSwitchChange (checked: Boolean) {
        sameValueChecked = checked
        if (sameValueChecked) {
            val clockUpdated = _chessClock.value
            clockUpdated?.secondPlayerTime = clockUpdated?.firstPlayerTime!!
            _chessClock.value = clockUpdated
        }
    }

    fun onIncrementTimeSet (minutes: Int, seconds: Int) {
        val timeMillis = seconds * ONE_SECOND + minutes * ONE_MINUTE
        val clockUpdated = _chessClock.value
        clockUpdated?.increment = timeMillis
        _chessClock.value = clockUpdated
    }
}