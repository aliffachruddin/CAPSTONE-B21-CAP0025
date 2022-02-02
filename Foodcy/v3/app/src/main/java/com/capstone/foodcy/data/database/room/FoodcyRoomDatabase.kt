package com.capstone.foodcy.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RoomUserAnswer::class, RoomUserCluster::class, RoomUserFavorit::class],
    version = 1)
abstract class FoodcyRoomDatabase : RoomDatabase(){

    abstract fun foodcyDao() : FoodcyDao

    companion object {
        @Volatile
        private var INSTANCE: FoodcyRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodcyRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FoodcyRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FoodcyRoomDatabase::class.java, "foodcy_database.db")
                        .build()
                }
            }
            return INSTANCE as FoodcyRoomDatabase
        }
    }
}