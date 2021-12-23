package com.capstone.foodcy.ui.quiz

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.firebase.User

class ResultViewModel : ViewModel() {

    private var u = User()
    val user = u.user

    fun insert(roomUserCluster: RoomUserCluster, application: Application) {
        val mFoodcyRepository= FoodcyRepository(application)
        mFoodcyRepository.insertUserCluster(roomUserCluster)
    }

    fun getUserCluster(application: Application) : LiveData<List<RoomUserCluster>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserCluster(user?.uid.toString())
    }

    fun update(roomUserCluster: RoomUserCluster, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.updateUserCluster(roomUserCluster)
    }
}