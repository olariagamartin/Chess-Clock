package com.themarto.chessclock.clocks

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.MyCountDownTimer
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.NOT_STARTED
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_SECOND
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.PAUSED
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.RUNNING
import kotlinx.coroutines.launch

class ClocksViewModel(application: Application, private var clockId: Long) : ViewModel() {

    private val database = ChessClockDatabase.getInstance(application, viewModelScope)
        .chessClockDao

    private var clock: ChessClock? = null

    companion object {
        const val TURN_1 = 1
        const val TURN_2 = 2
        const val NO_TURN = 0
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
    private val _playerOneMoves = MutableLiveData<Int>()
    val playerOneMoves: LiveData<Int> get() = _playerOneMoves
    private val _showAlertTimeOne = MutableLiveData<Boolean>()
    val showAlertTimeOne: LiveData<Boolean> get() = _showAlertTimeOne

    // Timer 2
    private lateinit var timer2: MyCountDownTimer
    private val _timeLeft2 = MutableLiveData<Long>()
    private val timeLeft2: LiveData<Long> get() = _timeLeft2
    val timeLeftString2 = Transformations.map(timeLeft2) {
        DateUtils.formatElapsedTime(it / ONE_SECOND)
    }
    private val _playerTwoMoves = MutableLiveData<Int>()
    val playerTwoMoves: LiveData<Int> get() = _playerTwoMoves
    private val _showAlertTimeTwo = MutableLiveData<Boolean>()
    val showAlertTimeTwo: LiveData<Boolean> get() = _showAlertTimeTwo

    private val _updateHintText = MutableLiveData<Boolean>()
    val updateHintText: LiveData<Boolean> get() = _updateHintText

    private val _gamePaused = MutableLiveData<Boolean>()
    val gamePaused: LiveData<Boolean> get() = _gamePaused

    private val _navigateToSettins = MutableLiveData<Boolean>()
    val navigateToSettings: LiveData<Boolean> get() = _navigateToSettins

    private val _turn = MutableLiveData<Int>()
    val turn: LiveData<Int> get() = _turn

    val guidelinePercentage = Transformations.map(turn) {
        when (turn.value) {
            TURN_1 -> PERCENT_66
            TURN_2 -> PERCENT_33
            else -> PERCENT_50
        }
    }

    var timeAlert = 0L

    private var timeAlertCheck1: (Long) -> Unit = {}
    private var timeAlertCheck2: (Long) -> Unit = {}

    init {
        initializeCurrentClock()
        _gamePaused.value = true
        _turn.value = NO_TURN
        _playerOneMoves.value = 0
        _playerTwoMoves.value = 0
        _showAlertTimeOne.value = false
        _showAlertTimeTwo.value = false
        initializeTimer1() //todo: move to initializeCurrentClock
        initializeTimer2()
    }

    fun setCurrentClockId(id: Long){
        if (timer1.state == NOT_STARTED && timer2.state == NOT_STARTED) {
            clockId = id
            initializeCurrentClock()
        }
    }

    private fun initializeCurrentClock() {
        if (clockId != (-1).toLong()) {
            viewModelScope.launch {
                clock = database.get(clockId)
                initializeTimer1()
                initializeTimer2()
                setAlertTimeChecks()
            }
        }
    }

    private fun initializeTimer1() {
        val firstPlayerTime = clock?.firstPlayerTime ?: ONE_MINUTE*5
        _timeLeft1.value = firstPlayerTime
        timer1 = object : MyCountDownTimer(firstPlayerTime, ONE_SECOND / 100) {
            override fun onTickTimer(millisUntilFinished: Long) {
                _timeLeft1.value = millisUntilFinished
                timeAlertCheck1 (millisUntilFinished)
            }

            override fun onFinishTimer() { }

        }
    }

    private fun initializeTimer2() {
        val secondPlayerTime = clock?.secondPlayerTime ?: ONE_MINUTE*5
        _timeLeft2.value = secondPlayerTime
            timer2 = object : MyCountDownTimer(secondPlayerTime, ONE_SECOND / 100) {
            override fun onTickTimer(millisUntilFinished: Long) {
                _timeLeft2.value = millisUntilFinished
                timeAlertCheck2 (millisUntilFinished)
            }

            override fun onFinishTimer() { }

        }
    }

    fun onClickClock1() {
        // todo: extract methods
        when (timer1.state) {
            NOT_STARTED -> {
                timer2.startTimer()
                if (turn.value == NO_TURN) _updateHintText.value = true
                _turn.value = TURN_2
                _gamePaused.value = false
            }
            RUNNING -> {
                timer1.pauseTimer()
                timer2.resumeTimer()
                _turn.value = TURN_2
                clock?.let {
                    timer1.incrementTime(it.increment)
                    _timeLeft1.value = _timeLeft1.value?.plus(it.increment)
                }
                _playerOneMoves.value = _playerOneMoves.value?.plus(1)
                timeAlertCheck1(timeLeft1.value!!)
            }
            PAUSED -> {
                if (turn.value == TURN_1) {
                    timer1.resumeTimer()
                    _gamePaused.value = false
                }
            }
        }
    }

    fun onClickClock2() {
        when (timer2.state) {
            NOT_STARTED -> {
                timer1.startTimer()
                if (turn.value == NO_TURN) _updateHintText.value = true
                _turn.value = TURN_1
                _gamePaused.value = false
            }
            RUNNING -> {
                timer2.pauseTimer()
                timer1.resumeTimer()
                _turn.value = TURN_1
                clock?.let {
                    timer2.incrementTime(it.increment)
                    _timeLeft2.value = _timeLeft2.value?.plus(it.increment)
                }
                _playerTwoMoves.value = _playerTwoMoves.value?.plus(1)
                timeAlertCheck2(timeLeft2.value!!)
            }
            PAUSED -> {
                if (turn.value == TURN_2) {
                    timer2.resumeTimer()
                    _gamePaused.value = false
                }
            }
        }
    }

    fun goToSettingsAction() {
        _navigateToSettins.value = true
    }

    fun onSettingsNavigated() {
        _navigateToSettins.value = false
    }

    fun onClickPause() {
        pauseTimers()
        _gamePaused.value = true
    }

    private fun setAlertTimeChecks () {
        if (timeAlert > 0) {
            if (timeAlert < clock?.firstPlayerTime ?: ONE_MINUTE * 5) {
                timeAlertCheck1 = {
                    if (it < timeAlert && showAlertTimeOne.value == false) {
                        _showAlertTimeOne.value = true
                    } else if (it > timeAlert && showAlertTimeOne.value == true) {
                        _showAlertTimeOne.value = false
                    }
                }
            }
            if (timeAlert < clock?.secondPlayerTime ?: ONE_MINUTE * 5) {
                timeAlertCheck2 = {
                    if (it < timeAlert && showAlertTimeTwo.value == false) {
                        _showAlertTimeTwo.value = true
                    } else if (it > timeAlert && showAlertTimeTwo.value == true) {
                        _showAlertTimeTwo.value = false
                    }
                }
            }
        }
    }

    private fun pauseTimers() {
        timer1.pauseTimer()
        timer2.pauseTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timer1.cancelTimer()
        timer2.cancelTimer()
    }
}