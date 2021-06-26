package com.themarto.chessclock.clocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ClocksViewModel() : ViewModel() {

    companion object {
        private const val TURN_1 = 1
        private const val TURN_2 = 2
        private const val PERCENT_33 = 0.33F
        private const val PERCENT_66 = 0.66F
        private const val PERCENT_50 = 0.50F
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

    fun onClickClock1(){
        _turn.value = TURN_2
    }

    fun onClickClock2(){
        _turn.value = TURN_1
    }
}