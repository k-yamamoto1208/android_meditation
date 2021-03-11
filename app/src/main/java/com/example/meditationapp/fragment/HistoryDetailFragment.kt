package com.example.meditationapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.meditationapp.R
import com.example.meditationapp.databinding.FragmentHistoryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {

    lateinit var binding: FragmentHistoryDetailBinding
    private val args: HistoryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_detail, container, false)
        setHasOptionsMenu(true)
        val root = binding.root
        binding.history = args.history
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_history_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_edit -> {
                val action = HistoryDetailFragmentDirections.historyDetailToHistoryEdit(args.history)
                findNavController().navigate(action)
            }
            else -> findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}