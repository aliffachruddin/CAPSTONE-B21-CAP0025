package com.capstone.foodcy.data.firebase

import com.capstone.foodcy.data.entity.UserDetailEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class User {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    val user : FirebaseUser? = auth.currentUser

    fun getUserDetail() : UserDetailEntity {
        var userDetail = UserDetailEntity("", "", null, "", "")
        if (user != null) {
            val uid = user.uid
            val email = user.email
            val photo = user.photoUrl
            val name = user.displayName
            val phone = user.phoneNumber
            userDetail = UserDetailEntity(uid, email, photo, name, phone)
        }

        return userDetail
    }

}