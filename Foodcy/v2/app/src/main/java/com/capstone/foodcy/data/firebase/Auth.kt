package com.capstone.foodcy.data.firebase

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.UserEntity
import com.capstone.foodcy.ui.carousel.CarouselActivity
import com.capstone.foodcy.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class Auth {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()


    fun register(user : UserEntity, activity: Activity) {
        val email = user.email
        val password = user.password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                if (it.isSuccessful) {
                    login(user, activity)
                } else {
                    Toast.makeText(activity, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun login(user: UserEntity, activity: Activity) {
        val email = user.email
        val password = user.password

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                if (it.isSuccessful) {
                    if(isUserVerified()) {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity.startActivity(intent)
                    } else {
                        verify(activity)
                    }
                } else {
                    Toast.makeText(activity, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logout(activity: Activity) : Boolean {
        auth.signOut()

        val intent = Intent(activity, CarouselActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)

        return true
    }

    fun isUserVerified() : Boolean {
        val user = auth.currentUser
        var isVerify = true

        if (user != null) {
            isVerify = user.isEmailVerified
        }
        return isVerify
    }

    fun verify(activity: Activity) {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val msg = activity.getString(R.string.verify_success_message)
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                val msg = activity.getString(R.string.verify_success_message)
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            }

            logout(activity)
        }
    }

    fun isLogin(activity: Activity) {
        if (auth.currentUser != null) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }
}