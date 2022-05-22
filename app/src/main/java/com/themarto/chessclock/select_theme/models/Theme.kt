package com.themarto.chessclock.select_theme.models

import androidx.annotation.ColorRes

data class Theme(
    val id: Int,
    @ColorRes val playerOneColor: Int,
    @ColorRes val playerOneTextColor: Int,
    @ColorRes val playerTwoColor: Int,
    @ColorRes val playerTwoTextColor: Int
)