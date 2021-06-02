package com.capstone.foodcy.ui.recommendation

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.csv.FoodcyCsv
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.data.firebase.User

class RecommendationViewModel : ViewModel() {

    private val foodcyCsv = FoodcyCsv()

    private var u = User()
    val user = u.user

    fun getListRecommendation(cluster : Int, activity: Activity) : List<FoodEntity> = foodcyCsv.getRecommendations(cluster, activity)

    fun getUserCluster(application: Application) : LiveData<List<RoomUserCluster>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserCluster(user?.uid.toString())
    }
}