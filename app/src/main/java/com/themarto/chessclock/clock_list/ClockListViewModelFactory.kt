package com.themarto.chessclock.clock_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ClockListViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClockListViewModel::class.java)) {
            return ClockListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}