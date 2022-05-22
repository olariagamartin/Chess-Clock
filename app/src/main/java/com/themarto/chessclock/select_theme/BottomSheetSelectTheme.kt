package com.themarto.chessclock.select_theme

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.themarto.chessclock.R
import com.themarto.chessclock.SettingsFragment
import com.themarto.chessclock.databinding.BottomSheetSelectThemeBinding
import com.themarto.chessclock.select_theme.models.Theme

class BottomSheetSelectTheme : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSelectThemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    companion object {

        fun newInstance () : BottomSheetSelectTheme {
            return BottomSheetSelectTheme()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSelectThemeBinding.inflate(inflater, container, false)

        preferences = requireActivity().getSharedPreferences(SettingsFragment.PREFERENCES_NAME, Context.MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews () {
        binding.gridThemes.adapter = ThemeListAdapter(requireContext(), getThemes()) { theme ->
            saveTheme(theme)
            restartActivity()
        }
    }

    // for fast implementation
    private fun getThemes () : List<Theme> {
        return arrayListOf(
            Theme(ThemeUtils.THEME_ONE, R.color.grey_800, R.color.grey_100, R.color.grey_100, R.color.grey_800),
            Theme(ThemeUtils.THEME_TWO, R.color.hot_pink, R.color.grey_50, R.color.cyan, R.color.grey_900),
            Theme(ThemeUtils.THEME_THREE, R.color.aquamarine, R.color.grey_50, R.color.salmon, R.color.grey_900),
            Theme(ThemeUtils.THEME_FOUR, R.color.lime_green, R.color.grey_900, R.color.forest_green, R.color.grey_100),
            Theme(ThemeUtils.THEME_FIVE, R.color.yellow, R.color.dark_blue, R.color.dark_blue, R.color.yellow),
            Theme(ThemeUtils.THEME_SIX, R.color.red_orange, R.color.grey_100, R.color.black_lean, R.color.grey_100)
        )
    }

    private fun saveTheme(theme: Int) {
        preferences.edit().putInt(ThemeUtils.SELECTED_THEME_KEY, theme).commit()
    }

    private fun restartActivity() {
        activity?.recreate()
    }

}