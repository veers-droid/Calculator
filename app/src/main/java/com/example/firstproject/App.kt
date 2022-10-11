package com.example.firstproject

import android.app.Application
import android.support.v7.app.AppCompatDelegate

//need to set theme for our application
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
    }
}
