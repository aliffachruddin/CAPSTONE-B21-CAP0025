package com.capstone.foodcy.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodEntity (
    val cluster : String,
    val id: String,
    val name: String,
    val ingredients: String,
    val diet:String,
    val prep_time: String,
    val cook_time: String,
    val flavor: String,
    val course: String,
    val diabetic: String,
    val calories: String,
    val halal: String,
    val image: String
        ):Parcelable