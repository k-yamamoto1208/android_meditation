package com.example.meditationapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditationapp.Event
import com.example.meditationapp.HistoryRepository
import com.example.meditationapp.db.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryListViewModel @Inject constructor(private val repository: HistoryRepository): ViewModel() {

    val historyList = MutableLiveData<List<History>>()
    val isEmpty = MutableLiveData(false)
    val transitToHistoryDetail = MutableLiveData<Event<String>>()

    init {
        findList()
    }

    private fun findList(){
        viewModelScope.launch(Dispatchers.IO) {
            historyList.postValue(repository.findAllHistoryInDB())
        }
    }

    fun clickList(position: Int){
        transitToHistoryDetail.value = Event(position.toString())
    }
}