package com.example.meditationapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditationapp.Application
import com.example.meditationapp.Event
import com.example.meditationapp.repository.HistoryRepository
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
    private val _transitToTop = MutableLiveData<Event<String>>()
    val transitToTop: LiveData<Event<String>>
        get() = _transitToTop

    fun clickComplete(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHistoryInDB(history.id, memo.value!!)
        }
        _transitToTop.value = Event(Application.instance.getString(R.string.transit_flag_from_edit_to_top))
    }
}