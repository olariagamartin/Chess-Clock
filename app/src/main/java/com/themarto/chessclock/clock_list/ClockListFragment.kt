package com.themarto.chessclock.clock_list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.themarto.chessclock.databinding.FragmentClockListBinding
import com.themarto.chessclock.utils.ChessUtils
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

        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        val viewModelFactory = ClockListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClockListViewModel::class.java)

        val currentClockId = preferences.getLong(CURRENT_CLOCK_KEY, -1)
        val adapter = ClockListAdapter(currentClockId)
        adapter.putOnClickItem { clockId ->
            preferences.edit().putLong(CURRENT_CLOCK_KEY, clockId).apply()
            adapter.currentClockId = clockId
            adapter.notifyDataSetChanged()
        }

        viewModel.clocks.observe(viewLifecycleOwner) {
            adapter.data = it
        }

        binding.clockList.adapter = adapter

        binding.addClock.setOnClickListener{
            val action = ClockListFragmentDirections.actionClockListFragmentToCreateEditClockFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}