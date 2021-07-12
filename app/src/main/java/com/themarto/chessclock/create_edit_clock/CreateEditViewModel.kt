package com.themarto.chessclock.create_edit_clock

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.chessclock.database.ChessClockDatabase

class CreateEditViewModel(
    application: Application,
    private val clockId: Long,
    private val editOption: Boolean
) : ViewModel() {

    private val database = ChessClockDatabase.getInstance(application, viewModelScope)
        .chessClockDao


}