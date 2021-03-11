package com.example.meditationapp.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditationapp.Application
import com.example.meditationapp.Event
import com.example.meditationapp.HistoryRepository
import com.example.meditationapp.R
import com.example.meditationapp.db.History
import com.example.meditationapp.fragment.HistoryCreateFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryCreateViewModel @Inject constructor(private val historyRepository: HistoryRepository): ViewModel() {

    lateinit var args: HistoryCreateFragmentArgs

    val transitToTop = MutableLiveData<Event<String>>()

    @SuppressLint("SimpleDateFormat")
    val date = MutableLiveData(SimpleDateFormat("yyyy-MM-dd").format(Date()))
    val memo = MutableLiveData<String?>(null)

    fun clickSave(){
        val history = History(0, date.value, args.time, args.music, memo.value)
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.createHistoryInDB(history)
        }
        transitToTop.value = Event(Application.instance.getString(R.string.transit_flag_to_top))
    }
}
