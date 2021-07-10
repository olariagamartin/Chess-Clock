package com.themarto.chessclock.clocks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themarto.chessclock.clock_list.ClockListViewModel
import java.lang.IllegalArgumentException

class ClocksViewModelFactory(private val application: Application, private val clockId: Long)
    : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClocksViewModel::class.java)) {
            return ClocksViewModel(application, clockId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}