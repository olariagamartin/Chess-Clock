package com.themarto.chessclock.clocks

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.themarto.chessclock.utils.MyCountDownTimer
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.NOT_STARTED
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_SECOND
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.PAUSED
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.RUNNING

class ClocksViewModel() : ViewModel() {

    companion object {
        private const val TURN_1 = 1
        private const val TURN_2 = 2
        private const val PERCENT_33 = 0.33F
        private const val PERCENT_66 = 0.66F
        private const val PERCENT_50 = 0.50F
    }

    // Timer 1
    private lateinit var timer1: MyCountDownTimer
    private val _timeLeft1 = MutableLiveData<Long>()
    private val timeLeft1: LiveData<Long> get() = _timeLeft1
    val timeLeftString1 = Transformations.map(timeLeft1) {
        DateUtils.formatElapsedTime(it / ONE_SECOND)
    }

    // Timer 2
    private lateinit var timer2: MyCountDownTimer
    private val _timeLeft2 = MutableLiveData<Long>()
    private val timeLeft2: LiveData<Long> get() = _timeLeft2
    val timeLeftString2 = Transformations.map(timeLeft2) {
        DateUtils.formatElapsedTime(it / ONE_SECOND)
    }

    private val _turn = MutableLiveData<Int>()
    private val turn: LiveData<Int> get() = _turn

    val guidelinePercentage = Transformations.map(turn) {
        when (turn.value) {
            TURN_1 -> PERCENT_66
            TURN_2 -> PERCENT_33
            else -> PERCENT_50
        }
    }

    init {
        initializeTimer1()
        initializeTimer2()
    }

    private fun initializeTimer1() {
        timer1 = object : MyCountDownTimer(ONE_MINUTE * 5, ONE_SECOND / 100) {
            override fun onTickTimer(millisUntilFinished: Long) {
                _timeLeft1.value = millisUntilFinished
            }

            override fun onFinishTimer() {
                //TODO("Not yet implemented")
            }

        }
    }

    private fun initializeTimer2() {
        timer2 = object : MyCountDownTimer(ONE_MINUTE * 5, ONE_SECOND / 100) {
            override fun onTickTimer(millisUntilFinished: Long) {
                _timeLeft2.value = millisUntilFinished
            }

            override fun onFinishTimer() {
                //TODO("Not yet implemented")
            }

        }
    }

    fun onClickClock1() {
        when (timer1.state) {
            NOT_STARTED -> timer2.startTimer()
            RUNNING -> {
                timer1.pauseTimer()
                timer2.resumeTimer()
            }
        }
        _turn.value = TURN_2
    }

    fun onClickClock2() {
        when (timer2.state) {
            NOT_STARTED -> timer1.startTimer()
            RUNNING -> {
                timer2.pauseTimer()
                timer1.resumeTimer()
            }
        }
        _turn.value = TURN_1
    }

    override fun onCleared() {
        super.onCleared()
        timer1.cancelTimer()
        timer2.cancelTimer()
    }
}