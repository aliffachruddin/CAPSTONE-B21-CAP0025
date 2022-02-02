package com.capstone.foodcy.ui.setting

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.firebase.Auth
import com.capstone.foodcy.data.firebase.User

class SettingViewModel : ViewModel() {
    private var u = User()
    val user = u.user

    private val auth = Auth()
    fun setLogout(activity: Activity) = auth.logout(activity)

    fun getAllUserAnswer(application: Application) : LiveData<List<RoomUserAnswer>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserAnswer(user?.uid.toString())
    }

    fun deleteUserAnswer(roomUserAnswer: RoomUserAnswer, application: Application) {
        val mFoodcyRepository= FoodcyRepository(application)
        mFoodcyRepository.deleteUserAnswer(roomUserAnswer)
    }

    fun getUserCluster(application: Application) : LiveData<List<RoomUserCluster>> {
        val mFoodcyRepository = FoodcyRepository(application)
        return mFoodcyRepository.getUserCluster(user?.uid.toString())
    }

    fun deleteUserCluster(roomUserCluster: RoomUserCluster, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.deleteUserCluster(roomUserCluster)
    }

    fun getUserFavorit(application :Application) : LiveData<List<RoomUserFavorit>> {
        val mFoodcyRepository = FoodcyRepository(application)
        return mFoodcyRepository.getUserFavorit(user?.uid.toString())
    }

    fun deleteUserFavorit(roomUserFavorit: RoomUserFavorit, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.deleteUserFavorit(roomUserFavorit)
    }
}