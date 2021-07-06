package com.themarto.chessclock.clock_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.themarto.chessclock.databinding.FragmentClockListBinding

class ClockListFragment : Fragment() {

    private var _binding: FragmentClockListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClockListBinding.inflate(inflater, container, false)

        binding.addClock.setOnClickListener{
            Toast.makeText(context, "Add clock", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}