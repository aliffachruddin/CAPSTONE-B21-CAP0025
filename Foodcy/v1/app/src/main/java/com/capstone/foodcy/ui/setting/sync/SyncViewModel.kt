package com.capstone.foodcy.ui.setting.sync

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.firebase.User

class SyncViewModel : ViewModel() {
    private var u = User()
    val user = u.user

    fun getAllUserAnswer(application: Application) : LiveData<List<RoomUserAnswer>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserAnswer(user?.uid.toString())
    }

    fun insertUserAnswer(roomUserAnswer: RoomUserAnswer, application : Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.insertUserAnswer(roomUserAnswer)
    }

    fun updateUserAnswer(roomUserAnswer: RoomUserAnswer, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.updateUserAnswer(roomUserAnswer)
    }

    fun getAllUserCluster(application: Application) : LiveData<List<RoomUserCluster>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserCluster(user?.uid.toString())
    }

    fun insertUserCluster(roomUserCluster: RoomUserCluster, application : Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.insertUserCluster(roomUserCluster)
    }

    fun updateUserCluster(roomUserCluster: RoomUserCluster, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.updateUserCluster(roomUserCluster)
    }

    fun getAllUserFavorit(application: Application) : LiveData<List<RoomUserFavorit>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserFavorit(user?.uid.toString())
    }

    fun getFavUserById(application: Application, idFood: String) : LiveData<List<RoomUserFavorit>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserFavoritebyId(user?.uid.toString(), idFood)
    }

    fun insertUserFavorite(roomUserFavorit: RoomUserFavorit, application : Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.insertUserFavorit(roomUserFavorit)
    }

    fun updateUserFavorite(roomUserFavorit: RoomUserFavorit, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.updateUserFavorit(roomUserFavorit)
    }

    fun deleteUserFavorite(roomUserFavorit: RoomUserFavorit, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.deleteUserFavorit(roomUserFavorit)
    }
}