package com.themarto.chessclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.themarto.chessclock.select_theme.ThemeUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(ThemeUtils.getSelectedTheme(this))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}