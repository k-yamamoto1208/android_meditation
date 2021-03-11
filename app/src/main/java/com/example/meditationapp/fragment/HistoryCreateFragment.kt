package com.example.meditationapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.meditationapp.R
import com.example.meditationapp.databinding.FragmentHistoryCreateBinding
import com.example.meditationapp.viewModel.HistoryCreateViewModel
import com.example.meditationapp.viewModel.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryCreateFragment : Fragment() {

    lateinit var binding: FragmentHistoryCreateBinding
    private val args: HistoryCreateFragmentArgs by navArgs()
    private val viewModel: HistoryCreateViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        viewModel.args = args
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_create, container, false)
        val root = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.transitToTop.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}