package com.example.meditationapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.meditationapp.R
import com.example.meditationapp.databinding.FragmentHistoryEditBinding
import com.example.meditationapp.viewModel.HistoryEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryEditFragment : Fragment() {

    lateinit var binding: FragmentHistoryEditBinding
    val viewModel: HistoryEditViewModel by viewModels()
    val args: HistoryEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.history = args.history
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_edit, container, false)
        setHasOptionsMenu(true)
        val root = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.memo.value = viewModel.history.memo

        viewModel.transitToTop.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.history_edit_to_top)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}