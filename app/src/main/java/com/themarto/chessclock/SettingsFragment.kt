package com.themarto.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.themarto.chessclock.databinding.FragmentSettingsBinding
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_MINUTE
import com.themarto.chessclock.utils.MyCountDownTimer.Companion.ONE_SECOND
import com.themarto.chessclock.utils.MyTimePicker

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private lateinit var pref: SharedPreferences

    companion object {
        const val PREFERENCES_NAME = "com.themarto.chessclock.PREF_NAME"
        const val SOUND_AFTER_MOVE_KEY = "sound_after_move"
        const val VIBRATE_KEY = "vibrate"
        const val LOW_TIME_WARNING_KEY = "low_time_warning"
        const val ALERT_TIME_KEY = "alert_time"
    }

    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener {
            sharedPreferences, key ->
        when (key) {
            ALERT_TIME_KEY -> {
                val alertTime = sharedPreferences.getLong(ALERT_TIME_KEY, 0) / ONE_SECOND
                val alertTimeText = DateUtils.formatElapsedTime(alertTime)
                binding.alertTimeSummary.text = alertTimeText
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        pref = requireActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        loadData()

        // UI ACTIONS
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.soundSettingContainer.setOnClickListener {
            binding.soundSwitch.isChecked = !binding.soundSwitch.isChecked
        }

        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean(SOUND_AFTER_MOVE_KEY, isChecked)
                .apply()
        }

        binding.vibrateSettingContainer.setOnClickListener {
            binding.vibrateSwitch.isChecked = !binding.vibrateSwitch.isChecked
        }

        binding.vibrateSwitch.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean(VIBRATE_KEY, isChecked)
                .apply()
        }

        binding.lowTimeWarningSettingContainer.setOnClickListener {
            binding.lowTimeWarningSwitch.isChecked = !binding.lowTimeWarningSwitch.isChecked
        }

        binding.lowTimeWarningSwitch.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean(LOW_TIME_WARNING_KEY, isChecked)
                .apply()
        }

        binding.alertTimeSettingContainer.setOnClickListener {
            // todo: extract method
            val timePicker = MyTimePicker()
            timePicker.includeHours = false
            timePicker.setInitialTimeMillis(pref.getLong(ALERT_TIME_KEY, 0))
            timePicker.setOnTimeSetOption(getString(R.string.set_time_button)) { _,m,s ->
                val alertTimeLong = (m * ONE_MINUTE + s * ONE_SECOND)
                pref.edit().putLong(ALERT_TIME_KEY, alertTimeLong).apply()
            }
            timePicker.setTitle(getString(R.string.timer_picker_title))
            timePicker.show(parentFragmentManager, "time_picker")
        }
        //...

        return binding.root
    }

    private fun loadData() {
        val soundAfterMove = pref.getBoolean(SOUND_AFTER_MOVE_KEY, true)
        binding.soundSwitch.isChecked = soundAfterMove

        val vibrate = pref.getBoolean(VIBRATE_KEY, true)
        binding.vibrateSwitch.isChecked = vibrate

        val lowTimeWarning = pref.getBoolean(LOW_TIME_WARNING_KEY, false)
        binding.lowTimeWarningSwitch.isChecked = lowTimeWarning

        val alertTime = pref.getLong(ALERT_TIME_KEY, 0) / ONE_SECOND
        val alertTimeText = DateUtils.formatElapsedTime(alertTime)
        binding.alertTimeSummary.text = alertTimeText
    }

    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(prefListener)
    }

    override fun onPause() {
        super.onPause()
        pref.registerOnSharedPreferenceChangeListener(prefListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}