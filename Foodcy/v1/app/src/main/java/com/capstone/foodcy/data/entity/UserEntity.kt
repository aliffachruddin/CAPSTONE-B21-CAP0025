package com.capstone.foodcy.data.entity

import android.net.Uri

data class UserEntity(
    val email : String,
    val password : String
)

data class UserDetailEntity(
    val uid: String,
    val email : String?,
    val photoUrl: Uri?,
    val displayName: String?,
    val phoneNumber: String?
)
