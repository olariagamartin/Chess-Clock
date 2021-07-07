package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.ViewModel
import com.themarto.chessclock.database.ChessClock
import com.themarto.chessclock.database.ChessClockDatabase
import com.themarto.chessclock.utils.ChessUtils.Companion.BLITZ_THUMBNAIL
import com.themarto.chessclock.utils.ChessUtils.Companion.BULLET_THUMBNAIL
import com.themarto.chessclock.utils.ChessUtils.Companion.CLASSIC_THUMBNAIL
import com.themarto.chessclock.utils.ChessUtils.Companion.RAPID_THUMBNAIL
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE

class ClockListViewModel(application: Application): ViewModel() {
    private val database = ChessClockDatabase.getInstance(application).chessClockDao

    fun getDummyData(): List<ChessClock>{
        return arrayListOf(
            ChessClock(0, ONE_MINUTE, ONE_MINUTE, BULLET_THUMBNAIL, "Bullet"),
            ChessClock(1, 2*ONE_MINUTE, 2*ONE_MINUTE, BULLET_THUMBNAIL, "Bullet"),
            ChessClock(2, 5*ONE_MINUTE, 5*ONE_MINUTE, BLITZ_THUMBNAIL, "Blitz"),
            ChessClock(3, 10*ONE_MINUTE, 10*ONE_MINUTE, RAPID_THUMBNAIL, "Rapid"),
            ChessClock(4, 15*ONE_MINUTE, 15*ONE_MINUTE, RAPID_THUMBNAIL, "Rapid"),
            ChessClock(5, 60*ONE_MINUTE, 60*ONE_MINUTE, CLASSIC_THUMBNAIL, "Classic"),

        )
    }
}