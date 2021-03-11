package com.example.meditationapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meditationapp.R
import com.example.meditationapp.adapter.HistoryListAdapter
import com.example.meditationapp.databinding.FragmentHistoryListBinding
import com.example.meditationapp.viewModel.HistoryListViewModel
import com.example.meditationapp.viewModel.TopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_list.*

@AndroidEntryPoint
class HistoryListFragment : Fragment() {

    val viewModel: HistoryListViewModel by viewModels()
    lateinit var binding: FragmentHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_list, container, false)
        val root = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.historyList.observe(viewLifecycleOwner, {
            it?.let { list ->
                when{
                    (list.isEmpty()) -> viewModel.isEmpty.value = true
                    else -> {
                        viewModel.isEmpty.value = false
                        recycler_view.adapter = HistoryListAdapter(requireContext(), viewModel, viewLifecycleOwner)
                        recycler_view.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        })

        viewModel.transitToHistoryDetail.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { position ->
                val history = viewModel.historyList.value!![position.toInt()]
                val action = HistoryListFragmentDirections.historyListToHistoryDetail(history)
                findNavController().navigate(action)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }

}