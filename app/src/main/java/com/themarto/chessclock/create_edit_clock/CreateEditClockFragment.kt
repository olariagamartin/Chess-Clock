package com.themarto.chessclock.create_edit_clock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.themarto.chessclock.R
import com.themarto.chessclock.databinding.FragmentCreateEditClockBinding
import com.themarto.chessclock.utils.MyTimePicker

class CreateEditClockFragment : Fragment() {

    private var _binding: FragmentCreateEditClockBinding? = null
    private val binding: FragmentCreateEditClockBinding
        get() = _binding!!
    private lateinit var viewModel: CreateEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateEditClockBinding.inflate(inflater, container, false)
        val args = CreateEditClockFragmentArgs.fromBundle(requireArguments())
        setAppbarTitle(args.editOption)
        val application = requireActivity().application

        val viewModelFactory =
            CreateEditViewModelFactory(application, args.clockId, args.editOption)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateEditViewModel::class.java)

        // OBSERVERS
        viewModel.firstPlayerTime.observe(viewLifecycleOwner) {
            binding.playerOne.time.text = it
        }

        viewModel.secondPlayerTime.observe(viewLifecycleOwner) {
            binding.playerTwo.time.text = it
        }

        viewModel.closeFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
                viewModel.onCloseDone()
            }
        }
        //...

        // UI ACTIONS
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onNavigationClick()
        }

        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.save_option_menu -> {
                    viewModel.onSaveOptionMenuClick()
                    Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        })

        binding.playerOne.time.setOnClickListener {
            val timePicker = MyTimePicker()
            timePicker.maxValueHour = 10
            timePicker.setOnTimeSetOption("Ok") { h,m,s ->
                // todo: send to view model
                val timeText = "player one time $h:$m:$s"
                Toast.makeText(context, timeText, Toast.LENGTH_SHORT).show()
            }
            timePicker.setTitle("Select time")
            timePicker.show(parentFragmentManager, "time_picker")
        }

        binding.playerTwo.time.setOnClickListener {
            val timePicker = MyTimePicker()
            timePicker.maxValueHour = 10
            timePicker.setOnTimeSetOption("Ok") { h,m,s ->
                // todo: send to view model
                val timeText = "player two time $h:$m:$s"
                Toast.makeText(context, timeText, Toast.LENGTH_SHORT).show()
            }
            timePicker.setTitle("Select time")
            timePicker.show(parentFragmentManager, "time_picker")
        }

        binding.playerOne.increment.setOnClickListener {
            val timePicker = MyTimePicker()
            timePicker.includeHours = false
            timePicker.setOnTimeSetOption("Ok") { _,m,s ->
                // todo: send to view model
                val timeText = "player one increment $m:$s"
                Toast.makeText(context, timeText, Toast.LENGTH_SHORT).show()
            }
            timePicker.setTitle("Select time")
            timePicker.show(parentFragmentManager, "time_picker")
        }
        binding.playerTwo.increment.setOnClickListener {
            val timePicker = MyTimePicker()
            timePicker.includeHours = false
            timePicker.setOnTimeSetOption("Ok") { _,m,s ->
                // todo: send to view model
                val timeText = "player two increment $m:$s"
                Toast.makeText(context, timeText, Toast.LENGTH_SHORT).show()
            }
            timePicker.setTitle("Select time")
            timePicker.show(parentFragmentManager, "time_picker")
        }
        //....


        return binding.root
    }

    private fun setAppbarTitle(editOption: Boolean) {
        if (editOption) {
            binding.toolbar.title = getString(R.string.app_bar_edit_title)
        } else {
            binding.toolbar.title = getString(R.string.app_bar_create_title)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}