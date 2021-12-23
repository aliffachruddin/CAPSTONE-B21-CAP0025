package com.capstone.foodcy.utils.`object`

import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.SlideEntity

object Slides {

    fun getSlides(): List<SlideEntity> {
        val slides = ArrayList<SlideEntity>()

        slides.add(
            SlideEntity(
                "Welcome to Foodcy",
                R.drawable.undraw_healthy_options,
            "It is a platform for those who love to eat food calorie and nutritious",
            false))
        slides.add(
            SlideEntity(
                "About us",
                R.drawable.undraw_dev_focus,
                "We love live healthy and We think that you have to spare some time to eat food healty in home",
                true))

        return slides
    }
}