package com.capstone.foodcy.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.firebase.User

class DetailFoodViewModel:ViewModel() {

    private var u = User()
    val user = u.user

    fun insert(roomUserFavorit: RoomUserFavorit, application: Application) {
        val mFoodcyRepository= FoodcyRepository(application)
        mFoodcyRepository.insertUserFavorit(roomUserFavorit)
    }

    fun delete(roomUserFavorit: RoomUserFavorit, application: Application) {
        val mFoodcyRepository= FoodcyRepository(application)
        mFoodcyRepository.deleteUserFavorit(roomUserFavorit)
    }

    fun getUserFavoritedbyId(application: Application, idFood :String) : LiveData<List<RoomUserFavorit>>{
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserFavoritebyId(user?.uid.toString(), idFood)
    }
}