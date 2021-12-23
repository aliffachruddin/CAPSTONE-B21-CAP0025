package com.capstone.foodcy.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.entity.UserEntity
import com.capstone.foodcy.data.firebase.Auth

class LoginViewModel : ViewModel() {
    private val auth = Auth()

    fun setLogin(user: UserEntity, activity: Activity) = auth.login(user, activity)
}