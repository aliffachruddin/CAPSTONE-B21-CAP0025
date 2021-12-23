package com.capstone.foodcy.ui.favorite

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.csv.FoodcyCsv
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.data.firebase.User

class FavoriteViewModel : ViewModel() {

    private val foodcyCsv = FoodcyCsv()

    private var u = User()
    val user = u.user

    fun getAllFavFood(activity: Activity, list : List<String>) : List<FoodEntity> {
        return foodcyCsv.getFavorites(list, activity)
    }

    fun getUserFavorit(application: Application) : LiveData<List<RoomUserFavorit>> {
        val mFoodcyRepository = FoodcyRepository(application)
        return mFoodcyRepository.getUserFavorit(user?.uid.toString())
    }
}