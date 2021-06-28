package com.themarto.chessclock.clocks

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

        viewModel.guidelinePercentage.observe(viewLifecycleOwner, {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.guideline.setGuidelinePercent(it)
        })

        viewModel.timeLeftString1.observe(viewLifecycleOwner) {
            binding.clock1.textViewClock.text = it
        }

        viewModel.timeLeftString2.observe(viewLifecycleOwner) {
            binding.clock2.textViewClock.text = it
        }

        viewModel.gameStarted.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.clock1.textViewHint.visibility = View.INVISIBLE
                binding.clock2.textViewHint.visibility = View.INVISIBLE
            }
        }

        setClock1Theme()

        binding.clock1.root.setOnClickListener{
            viewModel.onClickClock1()
        }

        binding.clock2.root.setOnClickListener{
            viewModel.onClickClock2()
        }

        return binding.root
    }

    private fun setClock1Theme(){
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