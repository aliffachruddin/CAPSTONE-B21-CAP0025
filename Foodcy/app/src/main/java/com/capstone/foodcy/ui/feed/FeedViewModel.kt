package com.capstone.foodcy.ui.feed

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.firebase.Auth
import com.capstone.foodcy.data.firebase.User

class FeedViewModel : ViewModel() {

    private var u = User()
    val user = u.user

    private val auth = Auth()

    fun getIsVerified() = auth.isUserVerified()

    fun setVerify(activity: Activity) = auth.verify(activity)


}