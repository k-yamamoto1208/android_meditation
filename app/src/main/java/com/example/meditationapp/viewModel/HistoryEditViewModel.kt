package com.example.meditationapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditationapp.Application
import com.example.meditationapp.Event
import com.example.meditationapp.HistoryRepository
import com.example.meditationapp.R
import com.example.meditationapp.db.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryEditViewModel @Inject constructor(private val repository: HistoryRepository): ViewModel() {

    lateinit var history: History

    val memo = MutableLiveData("")
    val transitToTop = MutableLiveData<Event<String>>()

    fun clickComplete(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHistoryInDB(history.id, memo.value!!)
        }
        transitToTop.value = Event(Application.instance.getString(R.string.transit_flag_from_edit_to_top))
    }
}