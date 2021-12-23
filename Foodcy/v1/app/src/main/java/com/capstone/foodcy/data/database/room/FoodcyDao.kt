package com.capstone.foodcy.data.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface FoodcyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserAnswer(userAnswer: RoomUserAnswer)

    @Update
    fun updateUserAnswer(userAnswer: RoomUserAnswer)

    @Delete
    fun deleteUserAnswer(userAnswer: RoomUserAnswer)

    @Query("SELECT * from RoomUserAnswer where uid like :uid ORDER BY idQuestion ASC")
    fun getUserAnswer(uid: String) : LiveData<List<RoomUserAnswer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserCluster(userCluster: RoomUserCluster)

    @Update
    fun updateUserCluster(userCluster: RoomUserCluster)

    @Delete
    fun deleteUserCluster(userCluster: RoomUserCluster)

    @Query("SELECT * from RoomUserCluster where uid like :uid")
    fun getUserCluster(uid: String) : LiveData<List<RoomUserCluster>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserFavorit(usefFavorit: RoomUserFavorit)

    @Update
    fun updateUserFavorit(userFavorit: RoomUserFavorit)

    @Delete
    fun deleteUserFavorit(userFavorit: RoomUserFavorit)

    @Query("SELECT * from RoomUserFavorit where uid like :uid")
    fun getUserFavorit(uid: String) : LiveData<List<RoomUserFavorit>>

    @Query("SELECT * from RoomUserFavorit where uid like :uid and idFood like :id")
    fun getUserFavoritById(uid: String, id :String) : LiveData<List<RoomUserFavorit>>
}