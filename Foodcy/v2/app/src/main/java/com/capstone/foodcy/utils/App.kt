package com.capstone.foodcy.utils

import android.app.Application
import android.os.StrictMode
import com.capstone.foodcy.BuildConfig
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}