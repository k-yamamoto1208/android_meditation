package com.example.meditationapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application(){

    companion object {
        lateinit var instance: Application private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}