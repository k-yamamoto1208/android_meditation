package com.example.meditationapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.meditationapp.R
import com.example.meditationapp.databinding.HistoryListBinding
import com.example.meditationapp.viewModel.HistoryListViewModel

class HistoryListAdapter(
    private val context: Context, private val viewModel: HistoryListViewModel, private val parentLifecycleOwner: LifecycleOwner
    ): RecyclerView.Adapter<HistoryListAdapter.BindingHolder>() {

    class BindingHolder(val binding: HistoryListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<HistoryListBinding>(LayoutInflater.from(parent.context), R.layout.history_list, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.apply {
            lifecycleOwner = parentLifecycleOwner
            viewModel = this@HistoryListAdapter.viewModel
            this.position = position
        }
    }

    override fun getItemCount() = viewModel.historyList.value!!.size
}