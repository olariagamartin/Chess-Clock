package com.themarto.chessclock.clocks

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.themarto.chessclock.R
import com.themarto.chessclock.clocks.ClocksViewModel.Companion.TURN_1
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

        // Observers...
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
                binding.actionImagePauseSettings.setImageResource(R.drawable.ic_pause_btn)
            }
        }

        viewModel.navigateToSettings.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.gamePaused.observe(viewLifecycleOwner) {
            if(it == true){
                if (viewModel.turn.value == TURN_1) {
                    binding.clock1.textViewHint.text = "Tap to resume" //todo: extract string
                    binding.clock1.textViewHint.visibility = View.VISIBLE
                } else {
                    binding.clock2.textViewHint.text = "Tap to resume"
                    binding.clock2.textViewHint.visibility = View.VISIBLE
                }
            } else {
                binding.clock1.textViewHint.visibility = View.INVISIBLE
                binding.clock2.textViewHint.visibility = View.INVISIBLE
            }
        }
        //...

        setClock1Theme()

        // UI actions
        binding.clock1.root.setOnClickListener{
            viewModel.onClickClock1()
        }

        binding.clock2.root.setOnClickListener{
            viewModel.onClickClock2()
        }

        binding.actionImagePauseSettings.setOnClickListener{
            viewModel.onClickPauseSettings()
        }
        //...
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