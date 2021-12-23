package com.capstone.foodcy.ui.register

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.entity.UserEntity
import com.capstone.foodcy.data.firebase.Auth

class RegisterViewModel : ViewModel() {

    private val auth = Auth()

    fun setRegister(user: UserEntity, activity: Activity) = auth.register(user, activity)

}