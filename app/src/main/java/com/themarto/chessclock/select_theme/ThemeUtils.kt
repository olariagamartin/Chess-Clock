package com.themarto.chessclock.select_theme

import android.content.Context
import com.themarto.chessclock.R
import com.themarto.chessclock.SettingsFragment

object ThemeUtils {

    const val SELECTED_THEME_KEY = "chess_clock_selected_theme"

    const val THEME_ONE: Int = 1
    const val THEME_TWO: Int = 2
    const val THEME_THREE: Int = 3
    const val THEME_FOUR: Int = 4
    const val THEME_FIVE: Int = 5
    const val THEME_SIX: Int = 6

    fun getSelectedTheme(context: Context): Int {
        val selectTheme = context.getSharedPreferences(SettingsFragment.PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getInt(SELECTED_THEME_KEY, 0)

        return when (selectTheme) {
            THEME_SIX -> R.style.Theme_Six
            THEME_FIVE -> R.style.Theme_Five
            THEME_FOUR -> R.style.Theme_Four
            THEME_THREE -> R.style.Theme_Three
            THEME_TWO -> R.style.Theme_Two
            else -> R.style.Theme_One
        }
    }

    fun getSelectedThemeId(context: Context): Int {
        return context.getSharedPreferences(SettingsFragment.PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getInt(SELECTED_THEME_KEY, 0)
    }
}