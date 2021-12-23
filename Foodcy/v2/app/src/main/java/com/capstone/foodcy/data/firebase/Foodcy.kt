package com.capstone.foodcy.data.firebase

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class Foodcy : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}