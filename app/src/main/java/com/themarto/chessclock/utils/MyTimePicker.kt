package com.themarto.chessclock.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.themarto.chessclock.R

/**
 * @param onTimeSet what you want to do when click
 * on set time button. If include hour was set to false
 * that parameter will be always 0, you can ignore it
 */
class MyTimePicker(
    private val maxHours: Int = 10,
    private val maxMin: Int = 59,
    private val maxSec: Int = 59,
    private val onTimeSet: (hour: Int, minute: Int, second: Int) -> Unit
) : DialogFragment() {

    private lateinit var timePickerLayout: View
    private lateinit var hourPicker: NumberPicker
    private lateinit var minPicker: NumberPicker
    private lateinit var secPicker: NumberPicker

    private var initialHour: Int = 0
    private var initialMinute: Int = 0
    private var initialSeconds: Int = 0

    /**
     * Default value is true.
     * If set to false the hour picker is not
     * visible in the Dialog
     */
    var includeHours: Boolean = true

    private var title: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            timePickerLayout = requireActivity()
                .layoutInflater.inflate(R.layout.time_picker_content, null)

            setupTimePickerLayout()

            builder.setView(timePickerLayout)

            title?.let { title ->
                builder.setTitle(title)
            }
            builder.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, id ->
                    onTimeSet(hourPicker.value, minPicker.value, secPicker.value)
                })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * Set the initial values for the Time Picker.
     * If you don't use this method to specify the
     * initial values, the initial values will be 0.
     * @param hour initial value for hour
     * @param minute initial value for minute
     * @param second initial value for second
     */
    fun setInitialValues(hour: Int = 0, minute: Int = 0, second: Int = 0) {
        initialHour = hour
        initialMinute = minute
        initialSeconds = second
    }

    /**
     * @param title title for the Dialog
     */
    fun setTitle(title: String) {
        this.title = title
    }

    private fun setupTimePickerLayout() {
        hourPicker = timePickerLayout.findViewById<NumberPicker>(R.id.hours)
        minPicker = timePickerLayout.findViewById<NumberPicker>(R.id.minutes)
        secPicker = timePickerLayout.findViewById<NumberPicker>(R.id.seconds)

        if (!includeHours) {
            timePickerLayout.findViewById<LinearLayout>(R.id.hours_container)
                .visibility = View.GONE
            initialHour = 0
        }
        // Max values
        hourPicker.maxValue = maxHours
        minPicker.maxValue = maxMin
        secPicker.maxValue = maxSec

        // Initial values
        hourPicker.value = initialHour
        minPicker.value = initialMinute
        secPicker.value = initialSeconds
    }
}