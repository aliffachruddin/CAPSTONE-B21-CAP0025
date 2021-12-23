package com.capstone.foodcy.data.database.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class RoomUserFavorit (
    @ColumnInfo(name = "uid")
    var uid : String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idFood")
    var idFood : String
        ) : Parcelable