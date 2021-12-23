package com.capstone.foodcy.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.firebase.Auth

class MainViewModel : ViewModel() {
    private val auth = Auth()
    fun getIsLogin(activity: Activity) = auth.isLogin(activity)
}