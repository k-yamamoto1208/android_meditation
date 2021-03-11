package com.example.meditationapp.enum

import com.example.meditationapp.Application
import com.example.meditationapp.R

enum class MusicEnum(val value: String, val drawableId: Int, val rawId: Int) {
    AURORA(Application.instance.getString(R.string.select_music_aurora), R.drawable.aurora, R.raw.aurora),
    FOREST(Application.instance.getString(R.string.select_music_forest), R.drawable.forest, R.raw.forest),
    SPACE(Application.instance.getString(R.string.select_music_space), R.drawable.space, R.raw.space);

    companion object{
        fun getValues(): Array<String>{
            val valueList = arrayListOf<String>()
            for (i in values()){
                valueList.add(i.value)
            }
            val array = arrayOfNulls<String>(valueList.size)
            return valueList.toArray(array)
        }

        fun fromDrawableId(drawableId: Int): MusicEnum{
            val result = values().firstOrNull { it.drawableId == drawableId }
            result?.let {
                return it
            } ?: run {
                return FOREST
            }
        }
    }
}