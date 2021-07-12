package com.themarto.chessclock.create_edit_clock

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CreateEditViewModelFactory(
    private val application: Application,
    private val clockId: Long,
    private val editOption: Boolean
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEditViewModel::class.java)) {
            return CreateEditViewModel(application, clockId, editOption) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}