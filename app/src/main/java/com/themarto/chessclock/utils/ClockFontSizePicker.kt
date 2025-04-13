package com.themarto.chessclock.utils

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.themarto.chessclock.databinding.ClockFontSizePickerDialogBinding

class ClockFontSizePicker : DialogFragment() {

    private lateinit var binding: ClockFontSizePickerDialogBinding

    private var selectedFontSize: FontSize = FontSize.MEDIUM

    private var onConfirmOption: (FontSize) -> Unit = {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())

        binding = ClockFontSizePickerDialogBinding.inflate(layoutInflater)

        dialogBuilder.setView(binding.root)

        setupViews()

        return dialogBuilder.create()
    }

    private fun setupViews() {
        selectFontSize(selectedFontSize)

        binding.smallLayout.setOnClickListener {
            selectedFontSize = FontSize.SMALL
            selectFontSize(selectedFontSize)
        }

        binding.mediumLayout.setOnClickListener {
            selectedFontSize = FontSize.MEDIUM
            selectFontSize(selectedFontSize)
        }

        binding.largeLayout.setOnClickListener {
            selectedFontSize = FontSize.LARGE
            selectFontSize(selectedFontSize)
        }

        binding.confirmBtn.setOnClickListener {
            onConfirmOption(selectedFontSize)
            dismiss()
        }
    }

    private fun selectFontSize(fontSize: FontSize) {
        when (fontSize) {
            FontSize.SMALL -> {
                binding.smallCheck.visibility = View.VISIBLE
                binding.mediumCheck.visibility = View.INVISIBLE
                binding.largeCheck.visibility = View.INVISIBLE
            }
            FontSize.MEDIUM -> {
                binding.smallCheck.visibility = View.INVISIBLE
                binding.mediumCheck.visibility = View.VISIBLE
                binding.largeCheck.visibility = View.INVISIBLE
            }
            FontSize.LARGE -> {
                binding.smallCheck.visibility = View.INVISIBLE
                binding.mediumCheck.visibility = View.INVISIBLE
                binding.largeCheck.visibility = View.VISIBLE
            }
        }
    }

    fun setInitialSelection(fontSize: FontSize) {
        selectedFontSize = fontSize
    }

    fun setOnConfirmOption (onConfirm: (FontSize) -> Unit) {
        onConfirmOption = onConfirm
    }

    enum class FontSize {
        SMALL,
        MEDIUM,
        LARGE
    }
}