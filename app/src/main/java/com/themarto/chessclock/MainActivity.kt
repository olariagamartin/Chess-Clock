package com.themarto.chessclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.themarto.chessclock.databinding.ActivityMainBinding
import com.themarto.chessclock.select_theme.ThemeUtils

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setTheme(ThemeUtils.getSelectedTheme(this))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}