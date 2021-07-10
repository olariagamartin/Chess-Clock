package com.themarto.chessclock.utils

import android.content.res.Resources
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ChessUtils {
    companion object {
        const val BULLET = 5
        const val BLITZ = 10
        const val RAPID = 60
        const val CLASSIC = 100
        const val CURRENT_CLOCK_KEY = "CURRENT_CLOCK_KEY"
    }
}

class DataUtil {
    companion object {
        fun getDefaultClocks (): List<ChessClock> {
            return arrayListOf(
                ChessClock(firstPlayerTime = ONE_MINUTE, secondPlayerTime = ONE_MINUTE),
                ChessClock(firstPlayerTime = 2*ONE_MINUTE, secondPlayerTime = 2*ONE_MINUTE),
                ChessClock(firstPlayerTime = 5*ONE_MINUTE, secondPlayerTime = 5*ONE_MINUTE),
                ChessClock(firstPlayerTime = 10*ONE_MINUTE, secondPlayerTime = 10*ONE_MINUTE),
                ChessClock(firstPlayerTime = 15*ONE_MINUTE, secondPlayerTime = 15*ONE_MINUTE),
                ChessClock(firstPlayerTime = 60*ONE_MINUTE, secondPlayerTime = 60*ONE_MINUTE)
            )
        }

    }
}