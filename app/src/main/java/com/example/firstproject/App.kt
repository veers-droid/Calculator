package com.example.firstproject

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

//need to set theme for our application
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME)
    }
}
