package com.example.meditationapp.viewModel

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.*
import com.example.meditationapp.Application
import com.example.meditationapp.Event
import com.example.meditationapp.R
import com.example.meditationapp.enum.MusicEnum
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class TopViewModel: ViewModel() {

    var mediaPlayer: MediaPlayer? = null
    var music = R.raw.aurora
    //音楽再生ポジション
    var mediaPlayerPosition = 0
    var timer = Timer()
    private val timerTask: TimerTask.() -> Unit = {
        viewModelScope.launch {
            _time.value = time.value?.plus(1)
        }
    }

    val drawableId = MutableLiveData(R.drawable.aurora)
    val isPlay = MutableLiveData(true)
    val transitToCreate = MutableLiveData<Event<String>>()
    private val _time = MutableLiveData(0)
    private val time: LiveData<Int>
        get() = _time

    val timerText: LiveData<String> = Transformations.map(time) {
        val hour = it / 36000
        val minute = (it % 36000) / 600
        val second = ((it % 36000) % 600) / 10

        "%02d:%02d:%02d".format(hour, minute, second)
    }

    fun clickHistory(){
        transitToCreate.value = Event(Application.instance.getString(R.string.transit_flag_to_list))
    }

    fun clickPlay(context: Context){
        when(isPlay.value){
            true -> { // not playing
                startTimer()
                isPlay.value = false
                when(mediaPlayerPosition){
                    0 -> { //再生位置0
                        createMediaPlayer(context)
                        mediaPlayer!!.start()
                    }
                    else -> { //再生途中でポーズ状態
                        mediaPlayer?.seekTo(mediaPlayerPosition)
                        mediaPlayer?.start()
                    }
                }
            }
            false -> { //playing
                stopTimer()
                isPlay.value = true
                mediaPlayer?.pause()
                mediaPlayerPosition = mediaPlayer?.currentPosition ?: 0
                showHistoryCreateDialog(context)
            }
        }
    }

    fun clickMusic(context: Context){
        AlertDialog.Builder(context)
            .setTitle(Application.instance.getString(R.string.select_music_title))
            .setSingleChoiceItems(MusicEnum.getValues(), MusicEnum.fromDrawableId(drawableId.value!!).ordinal){ dialog, which ->
                drawableId.value = MusicEnum.values()[which].drawableId
                music = MusicEnum.values()[which].rawId
                //MediaPlayerをリセットし、再生位置を0に戻す
                mediaPlayer?.reset()
                mediaPlayerPosition = 0
                when (isPlay.value!!) {
                    true -> createMediaPlayer(context)
                    false -> {
                        createMediaPlayer(context)
                        mediaPlayer!!.start()
                    }
                }
                dialog.cancel()
            }
            .setNegativeButton(Application.instance.getString(R.string.select_music_cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun startTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(0, 100, timerTask)
    }

    private fun stopTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        _time.value = 0
    }

    private fun createMediaPlayer(context: Context){
        mediaPlayer = MediaPlayer.create(context, music)
        mediaPlayer!!.isLooping = true
    }

    /** 今回の履歴を残すかのDialog */
    private fun showHistoryCreateDialog(context: Context){
        AlertDialog.Builder(context)
            .setTitle(Application.instance.getString(R.string.history_create_dialog_title))
            .setPositiveButton(Application.instance.getString(R.string.history_create_dialog_yes)){ _, _ ->
                mediaPlayerPosition = 0
                //クリックイベントをFragmentに渡す
                transitToCreate.value = Event(Application.instance.getString(R.string.transit_flag_to_create))
                resetTimer()
            }
            .setNegativeButton(Application.instance.getString(R.string.history_create_dialog_no)){ dialog, _ ->
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }
}