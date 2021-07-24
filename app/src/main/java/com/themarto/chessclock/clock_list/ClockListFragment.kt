package com.themarto.chessclock.clock_list

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.themarto.chessclock.R
import com.themarto.chessclock.SettingsFragment.Companion.PREFERENCES_NAME
import com.themarto.chessclock.databinding.FragmentClockListBinding
import com.themarto.chessclock.utils.ChessUtils.Companion.CURRENT_CLOCK_KEY

class ClockListFragment : Fragment() {

    private var _binding: FragmentClockListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ClockListViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClockListBinding.inflate(inflater, container, false)
        val application = requireActivity().application

        preferences = requireActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        val currentClockId = preferences.getLong(CURRENT_CLOCK_KEY, -1)

        val viewModelFactory = ClockListViewModelFactory(application, currentClockId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClockListViewModel::class.java)

        val adapter = ClockListAdapter(currentClockId, getClockItemListener())

        // OBSERVERS...
        viewModel.clocks.observe(viewLifecycleOwner) {
            adapter.data = it
        }

        viewModel.currentClockId.observe(viewLifecycleOwner) {
            preferences.edit().putLong(CURRENT_CLOCK_KEY, it).apply()
            adapter.currentClockId = it
            adapter.notifyDataSetChanged()
        }
        //....

        binding.clockList.adapter = adapter

        // UI ACTIONS
        binding.toolbar.setNavigationOnClickListener { 
            findNavController().navigateUp()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sound_settings -> {
                    val action = ClockListFragmentDirections.actionClockListFragmentToSettingsFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        binding.addClock.setOnClickListener{
            val action = ClockListFragmentDirections.actionClockListFragmentToCreateEditClockFragment()
            findNavController().navigate(action)
        }
        //....

        return binding.root
    }

    private fun getClockItemListener (): ClockItemListener {
        return object : ClockItemListener{
            override fun onClickItem(clockId: Long) {
                viewModel.setCurrentClockId(clockId)
            }

            override fun onEditItem(clockId: Long) {
                val action = ClockListFragmentDirections.actionClockListFragmentToCreateEditClockFragment()
                action.clockId = clockId
                action.editOption = true
                findNavController().navigate(action)
            }

            override fun onRemoveItem(clockId: Long) {
                val removeItem = viewModel.onRemoveClick()
                if (removeItem) {
                   showConfirmDeleteDialog(clockId)
                } else {
                    showSnackBarOneClock()
                }
            }

        }
    }

    private fun showSnackBarOneClock() {
        // todo: extract string
        Snackbar.make(binding.root, "You must have at least one clock", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showConfirmDeleteDialog (clockId: Long) {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle("Delete clock") // todo: use string resources
        dialog.setMessage("Please confirm")
        dialog.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
            viewModel.removeItem(clockId)
        })
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->  })
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}