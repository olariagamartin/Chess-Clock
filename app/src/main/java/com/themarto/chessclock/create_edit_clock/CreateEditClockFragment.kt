package com.themarto.chessclock.create_edit_clock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.themarto.chessclock.databinding.FragmentCreateEditClockBinding

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

        val viewModelFactory = CreateEditViewModelFactory(application, args.clockId, args.editOption)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateEditViewModel::class.java)


        return binding.root
    }

    private fun setAppbarTitle(editOption: Boolean) {
        if (editOption) {
            Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Create", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}