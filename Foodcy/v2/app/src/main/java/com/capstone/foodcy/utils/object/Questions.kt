package com.capstone.foodcy.utils.`object`

import com.capstone.foodcy.data.entity.AnswerEntity
import com.capstone.foodcy.data.entity.QuestionEntity

object Questions {

    fun getQuestions(): List<QuestionEntity> {
        val questions = ArrayList<QuestionEntity>()

        val answer0 = ArrayList<AnswerEntity>()
        answer0.add((AnswerEntity(2, "India", 0)))

        val answer1 = ArrayList<AnswerEntity>()
        answer1.add((AnswerEntity(100, null, 0)))

        val answer2 = ArrayList<AnswerEntity>()
        answer2.add((AnswerEntity(99, null, 0)))

        val answer3 = ArrayList<AnswerEntity>()
        answer3.add((AnswerEntity(3, "Yes", 0)))
        answer3.add((AnswerEntity(4, "No", 1)))

        val answer4 = ArrayList<AnswerEntity>()
        answer4.add((AnswerEntity(5, "Non Vegetarian", 0)))
        answer4.add((AnswerEntity(6, "Vegetarian", 1)))

        val answer5 = ArrayList<AnswerEntity>()
        answer5.add((AnswerEntity(7, "Bitter", 0)))
        answer5.add((AnswerEntity(8, "Sour", 1)))
        answer5.add((AnswerEntity(9, "Spicy", 2)))
        answer5.add((AnswerEntity(10, "Sweet", 3)))

        val answer6 = ArrayList<AnswerEntity>()
        answer6.add((AnswerEntity(11, "Dessert", 0)))
        answer6.add((AnswerEntity(12, "Main Course", 1)))
        answer6.add((AnswerEntity(13, "Snack", 2)))
        answer6.add((AnswerEntity(14, "Starter", 3)))

        questions.add(
            QuestionEntity(
            1,
            "Country",
                answer0,
                false
            )
        )

        questions.add(
            QuestionEntity(
                2,
                "Cooking Time (Minutes)",
                answer1,
                true
            )
        )

        questions.add(
            QuestionEntity(
                3,
                "Calories (Kcal)",
                answer2,
                true
            )
        )

        questions.add(
            QuestionEntity(
                4,
                "Diabetes Friendly",
                answer3,
                false
            )
        )

        questions.add(
            QuestionEntity(
                5,
                "Diet",
                answer4,
                false
            )
        )

        questions.add(
            QuestionEntity(
                6,
                "Flavor",
                answer5,
                false
            )
        )

        questions.add(
            QuestionEntity(
                7,
                "Course",
                answer6,
                false
            )
        )

        return questions
    }
}