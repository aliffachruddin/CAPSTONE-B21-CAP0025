package com.capstone.foodcy.data.database.room

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FoodcyRepository(application: Application) {

    private val mFoodcyDao : FoodcyDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FoodcyRoomDatabase.getDatabase(application)
        mFoodcyDao = db.foodcyDao()
    }

    fun getUserAnswer(uid : String) : LiveData<List<RoomUserAnswer>> = mFoodcyDao.getUserAnswer(uid)

    fun insertUserAnswer(roomUserAnswer: RoomUserAnswer) {
        executorService.execute{ mFoodcyDao.insertUserAnswer(roomUserAnswer)}
    }

    fun updateUserAnswer(roomUserAnswer: RoomUserAnswer) {
        executorService.execute{ mFoodcyDao.updateUserAnswer(roomUserAnswer)}
    }

    fun deleteUserAnswer(roomUserAnswer: RoomUserAnswer) {
        executorService.execute{ mFoodcyDao.deleteUserAnswer(roomUserAnswer)}
    }

    fun getUserCluster(uid: String) : LiveData<List<RoomUserCluster>> = mFoodcyDao.getUserCluster(uid)

    fun insertUserCluster(roomUserCluster: RoomUserCluster) {
        executorService.execute { mFoodcyDao.insertUserCluster(roomUserCluster) }
    }

    fun updateUserCluster(roomUserCluster: RoomUserCluster) {
        executorService.execute { mFoodcyDao.updateUserCluster(roomUserCluster) }
    }

    fun deleteUserCluster(roomUserCluster: RoomUserCluster) {
        executorService.execute { mFoodcyDao.deleteUserCluster(roomUserCluster) }
    }

    fun getUserFavorit(uid: String) : LiveData<List<RoomUserFavorit>> = mFoodcyDao.getUserFavorit(uid)

    fun insertUserFavorit(roomUserFavorit: RoomUserFavorit) {
        executorService.execute { mFoodcyDao.insertUserFavorit(roomUserFavorit) }
    }

    fun updateUserFavorit(roomUserFavorit: RoomUserFavorit) {
        executorService.execute { mFoodcyDao.updateUserFavorit(roomUserFavorit) }
    }

    fun deleteUserFavorit(roomUserFavorit: RoomUserFavorit) {
        executorService.execute { mFoodcyDao.deleteUserFavorit(roomUserFavorit) }
    }

    fun getUserFavoritebyId(uid: String, id : String) : LiveData<List<RoomUserFavorit>> {
        return mFoodcyDao.getUserFavoritById(uid, id)
    }
}