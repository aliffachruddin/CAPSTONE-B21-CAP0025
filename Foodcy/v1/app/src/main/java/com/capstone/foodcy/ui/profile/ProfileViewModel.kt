package com.capstone.foodcy.ui.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.entity.QuestionEntity
import com.capstone.foodcy.data.firebase.User
import com.capstone.foodcy.utils.`object`.Questions

class ProfileViewModel : ViewModel() {

    private var u = User()
    val user = u.user

    fun getUserDetail() = u.getUserDetail()

    fun getAllQuestions() : List<QuestionEntity> {
        val questions = Questions.getQuestions()
        return questions
    }

    fun getAllUserAnswer(application: Application) : LiveData<List<RoomUserAnswer>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserAnswer(user?.uid.toString())
    }

}