package com.themarto.chessclock.clocks

import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.themarto.chessclock.R
import com.themarto.chessclock.databinding.FragmentClocksBinding

class ClocksFragment : Fragment() {

    private var _binding: FragmentClocksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ClocksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClocksBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ClocksViewModel::class.java)

        viewModel.guidelinePercentage.observe(viewLifecycleOwner, Observer {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.guideline.setGuidelinePercent(it)
        })

        setClock1Theme()

        binding.clock1.root.setOnClickListener{
            viewModel.onClickClock1()
        }

        binding.clock2.root.setOnClickListener{
            viewModel.onClickClock2()
        }

        return binding.root
    }

    fun setClock1Theme(){
        binding.clock1.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.clock1.textViewClock.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.clock1.textViewHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.clock1.textMovementsCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}