package com.themarto.chessclock.clocks

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.themarto.chessclock.R
import com.themarto.chessclock.clocks.ClocksViewModel.Companion.NO_TURN
import com.themarto.chessclock.clocks.ClocksViewModel.Companion.TURN_1
import com.themarto.chessclock.clocks.ClocksViewModel.Companion.TURN_2
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

        // Observers...
        viewModel.guidelinePercentage.observe(viewLifecycleOwner, {
            //TransitionManager.beginDelayedTransition(binding.root)
            binding.guideline.setGuidelinePercent(it)
        })

        viewModel.timeLeftString1.observe(viewLifecycleOwner) {
            binding.clock1.textViewClock.text = it
        }

        viewModel.timeLeftString2.observe(viewLifecycleOwner) {
            binding.clock2.textViewClock.text = it
        }

        viewModel.navigateToSettings.observe(viewLifecycleOwner) {
            if (it == true) {
                navigateToSettings()
            }
        }

        viewModel.updateHintText.observe(viewLifecycleOwner) {
            // todo: extract text
            binding.clock1.textViewHint.text = "Tap to resume"
            binding.clock2.textViewHint.text = "Tap to resume"
        }

        viewModel.gamePaused.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.actionPause.visibility = View.INVISIBLE
                binding.actionGoToSettings.visibility = View.VISIBLE
                binding.actionRestart.visibility = View.VISIBLE
                when (viewModel.turn.value) {
                    TURN_1 -> binding.clock1.textViewHint.visibility = View.VISIBLE
                    TURN_2 -> binding.clock2.textViewHint.visibility = View.VISIBLE
                    NO_TURN -> {
                        binding.clock1.textViewHint.visibility = View.VISIBLE
                        binding.clock2.textViewHint.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.actionGoToSettings.visibility = View.INVISIBLE
                binding.actionRestart.visibility = View.INVISIBLE
                binding.actionPause.visibility = View.VISIBLE
                binding.clock1.textViewHint.visibility = View.INVISIBLE
                binding.clock2.textViewHint.visibility = View.INVISIBLE
            }
        }
        //...

        setClock1Theme()

        // UI actions
        binding.clock1.root.setOnClickListener {
            viewModel.onClickClock1()
        }

        binding.clock2.root.setOnClickListener {
            viewModel.onClickClock2()
        }

        binding.actionPause.setOnClickListener {
            viewModel.onClickPause()
        }

        binding.actionRestart.setOnClickListener{
            resetClocksAlertDialog()
        }

        binding.actionGoToSettings.setOnClickListener{
            viewModel.goToSettingsAction()
        }
        //...
        return binding.root
    }

    private fun setClock1Theme() {
        binding.clock1.textViewClock.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.clock1.textViewHint.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.clock1.textMovementsCount.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
    }

    private fun resetClocksAlertDialog(){ //todo: extract text
        val restartBuilder = AlertDialog.Builder(requireContext())
        restartBuilder.apply {
            setTitle("Reset Timer?")
            setPositiveButton("Reset", DialogInterface.OnClickListener { dialog, which ->
                resetTimer()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                // nothing
            })
        }.create().show()
    }

    private fun resetTimer(){
        val navController: NavController = requireActivity().findNavController(R.id.navHostFragment)
        navController.run {
            popBackStack()
            navigate(R.id.clocksFragment)
        }
    }

    private fun navigateToSettings() {
        val action = ClocksFragmentDirections.actionClocksFragmentToClockListFragment()
        this.findNavController().navigate(action)
        viewModel.onSettingsNavigated()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}