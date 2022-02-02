package com.capstone.foodcy.data.database.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class RoomUserAnswer (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idQuestion")
    var idQuestion : Int = 0,

    @ColumnInfo(name = "answer")
    var answer : String? = null,

    @ColumnInfo(name = "score")
    var score : String? = null,

    @ColumnInfo(name = "uid")
    var uid : String? = null
) : Parcelable