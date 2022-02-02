package com.capstone.foodcy.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class QuestionEntity (
    val qId: Int,
    val question: String,
    val answer: List<AnswerEntity>,
    val isCustom: Boolean
    )

data class AnswerEntity (
    val aId: Int,
    val answer: String?,
    val score: Int
    )

@Parcelize
data class UserAnswer (
    val idQuestion: Int,
    val score: String,
    val answer: String
    ): Parcelable


data class UserAnswerFb (
    val uid : String = "",
    val idQuestion: Int = 0,
    val score: String ="",
    val answer: String =""
    )

data class UserCluster (
    val uid: String = "",
    val cluster: Int = 0
    )

