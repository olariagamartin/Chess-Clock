package com.themarto.chessclock.create_edit_clock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.themarto.chessclock.R
import com.themarto.chessclock.databinding.FragmentCreateEditClockBinding
import com.themarto.chessclock.utils.MyTimePicker
import com.themarto.chessclock.utils.ViewUtils

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
            binding.playerOneTime.text = it
        }

        viewModel.secondPlayerTime.observe(viewLifecycleOwner) {
            binding.playerTwoTime.text = it
        }

        viewModel.sameValueSwitch.observe(viewLifecycleOwner) {
            binding.sameTimeSwitch.isChecked = it
        }

        viewModel.incrementTime.observe(viewLifecycleOwner) {
            binding.incrementTime.text = it
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
                    true
                }
                else -> false
            }
        })

        binding.playerOneTime.setOnClickListener {
            showTimePickerForPlayerOne()
        }

        binding.playerTwoTime.setOnClickListener {
            showTimePickerForPlayerTwo()
        }

        binding.sameTimeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSameValueSwitchChange(isChecked)
        }

        binding.incrementTime.setOnClickListener {
            showTimePickerForIncrement()
        }
        //....

        ViewUtils.handleInsets(binding.root)

        return binding.root
    }

    private fun showTimePickerForPlayerOne () {
        val timePicker = MyTimePicker()
        timePicker.maxValueHour = 10
        viewModel.chessClock.value?.let { chessClock ->
            timePicker.setInitialTimeMillis(chessClock.firstPlayerTime)
        }
        timePicker.setOnTimeSetOption(getString(R.string.set_time_button)) { h,m,s ->
            viewModel.onFirstPlayerTimeSet(h, m, s)
        }
        timePicker.setTitle(getString(R.string.timer_picker_title))
        timePicker.show(parentFragmentManager, "time_picker")
    }

    private fun showTimePickerForPlayerTwo () {
        val timePicker = MyTimePicker()
        timePicker.maxValueHour = 10
        viewModel.chessClock.value?.let { chessClock ->
            timePicker.setInitialTimeMillis(chessClock.secondPlayerTime)
        }
        timePicker.setOnTimeSetOption(getString(R.string.set_time_button)) { h,m,s ->
            viewModel.onSecondPlayerTimeSet(h, m, s)
        }
        timePicker.setTitle(getString(R.string.timer_picker_title))
        timePicker.show(parentFragmentManager, "time_picker")
    }

    private fun showTimePickerForIncrement () {
        val timePicker = MyTimePicker()
        timePicker.includeHours = false
        viewModel.chessClock.value?.let { chessClock ->
            timePicker.setInitialTimeMillis(chessClock.increment)
        }
        timePicker.setOnTimeSetOption(getString(R.string.set_time_button)) { _,m,s ->
            viewModel.onIncrementTimeSet(m, s)
        }
        timePicker.setTitle(getString(R.string.timer_picker_title))
        timePicker.show(parentFragmentManager, "time_picker")
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