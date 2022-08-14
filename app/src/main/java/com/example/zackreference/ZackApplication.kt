package com.example.zackreference

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZackApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Log.d("App" , "Creation")
    }
}
