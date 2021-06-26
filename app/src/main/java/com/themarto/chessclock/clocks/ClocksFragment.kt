package com.themarto.chessclock.clocks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.themarto.chessclock.R
import com.themarto.chessclock.databinding.FragmentClocksBinding

class ClocksFragment : Fragment() {

    private var _binding: FragmentClocksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClocksBinding.inflate(inflater, container, false)

        setClock1Theme()

        binding.clock1.root.setOnClickListener{
            Toast.makeText(context, "Clock 1", Toast.LENGTH_SHORT).show()
            binding.guideline.setGuidelinePercent(0.33F)
        }

        binding.clock2.root.setOnClickListener{
            Toast.makeText(context, "Clock 2", Toast.LENGTH_SHORT).show()
            binding.guideline.setGuidelinePercent(0.66F)
        }

        return binding.root
    }

    fun setClock1Theme(){
        binding.clock1.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.clock1.textViewClock.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.clock1.textViewHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.clock1.textMovementsCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

}