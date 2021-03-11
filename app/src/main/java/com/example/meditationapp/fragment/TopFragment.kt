package com.example.meditationapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.meditationapp.R
import com.example.meditationapp.databinding.FragmentTopBinding
import com.example.meditationapp.enum.MusicEnum
import com.example.meditationapp.viewModel.TopViewModel

class TopFragment : Fragment() {

    private val viewModel: TopViewModel by viewModels()
    lateinit var binding: FragmentTopBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top, container, false)
        val root = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 遷移イベント
        viewModel.transitToCreate.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { content ->
                when(content){
                    getString(R.string.transit_flag_to_create) -> {
                        val action = TopFragmentDirections.topToHistoryCreate(viewModel.timerText.value!!, MusicEnum.fromDrawableId(viewModel.drawableId.value!!).value)
                        findNavController().navigate(action)
                    }
                    getString(R.string.transit_flag_to_list) -> findNavController().navigate(R.id.top_to_history_list)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.mediaPlayer?.release()
        viewModel.mediaPlayer = null
        viewModel.resetTimer()
    }
}