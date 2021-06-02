package com.capstone.foodcy.ui.quiz

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.database.room.FoodcyRepository
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.utils.`object`.Questions
import com.capstone.foodcy.data.entity.AnswerEntity
import com.capstone.foodcy.data.entity.QuestionEntity
import com.capstone.foodcy.data.firebase.User

class QuizViewModel : ViewModel() {

    private var u = User()
    val user = u.user



    fun getAllQuestions() : List<QuestionEntity> {
        val questions = Questions.getQuestions()
        return questions
    }

    fun getListAnswer(qId: Int) : List<AnswerEntity> {
        val questions = Questions.getQuestions()
        lateinit var answers : List<AnswerEntity>

        for (q in questions) {
            if (q.qId == qId) {
                answers = q.answer
            }
        }

        return answers
    }

    fun getAnswerScore(aId: Int, listAnswer : List<AnswerEntity>) : Int {
        var score = 0

        for (a in listAnswer) {
            if (a.aId == aId) {
                score = a.score
            }
        }

        return score
    }

    fun getAllUserAnswer(application: Application) : LiveData<List<RoomUserAnswer>> {
        val mFoodcyRepository= FoodcyRepository(application)
        return mFoodcyRepository.getUserAnswer(user?.uid.toString())
    }

    fun insert(roomUserAnswer: RoomUserAnswer, application : Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.insertUserAnswer(roomUserAnswer)
    }

    fun update(roomUserAnswer: RoomUserAnswer, application: Application) {
        val mFoodcyRepository = FoodcyRepository(application)
        mFoodcyRepository.updateUserAnswer(roomUserAnswer)
    }
}