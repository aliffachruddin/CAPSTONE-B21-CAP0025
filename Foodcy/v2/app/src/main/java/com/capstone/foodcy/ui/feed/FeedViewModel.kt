package com.capstone.foodcy.ui.feed

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.csv.FoodcyCsv
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.data.firebase.User

class FeedViewModel : ViewModel() {

    private val foodcyCsv = FoodcyCsv()

    private var u = User()

    val user = u.user

    fun getFood(activity: Activity) : List<FoodEntity> {
        return foodcyCsv.getAllFood(activity)
    }
}
