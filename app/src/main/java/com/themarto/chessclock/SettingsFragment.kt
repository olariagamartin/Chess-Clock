package com.themarto.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import com.themarto.chessclock.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private lateinit var pref: SharedPreferences

    companion object {
        const val SOUND_AFTER_MOVE_KEY = "sound_after_move"
        const val VIBRATE_KEY = "vibrate"
        const val LOW_TIME_WARNING_KEY = "low_time_warning"
        const val ALERT_TIME_KEY = "alert_time"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        pref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        loadData()

        // UI ACTIONS
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.soundSettingContainer.setOnClickListener {
            binding.soundSwitch.isChecked = !binding.soundSwitch.isChecked
            pref.edit().putBoolean(SOUND_AFTER_MOVE_KEY, binding.soundSwitch.isChecked)
                .apply()
        }

        binding.vibrateSettingContainer.setOnClickListener {
            binding.vibrateSwitch.isChecked = !binding.vibrateSwitch.isChecked
            pref.edit().putBoolean(VIBRATE_KEY, binding.vibrateSwitch.isChecked)
                .apply()
        }

        binding.lowTimeWarningSettingContainer.setOnClickListener {
            binding.lowTimeWarningSwitch.isChecked = !binding.lowTimeWarningSwitch.isChecked
            pref.edit().putBoolean(LOW_TIME_WARNING_KEY, binding.lowTimeWarningSwitch.isChecked)
                .apply()
        }

        // todo: add time selector to save on pref
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

        val alertTime = pref.getLong(ALERT_TIME_KEY, 0)
        val alertTimeText = DateUtils.formatElapsedTime(alertTime)
        binding.alertTimeSummary.text = alertTimeText
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}