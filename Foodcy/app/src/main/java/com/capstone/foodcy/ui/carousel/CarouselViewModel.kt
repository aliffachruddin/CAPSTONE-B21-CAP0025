package com.capstone.foodcy.ui.carousel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.utils.`object`.Slides
import com.capstone.foodcy.data.entity.SlideEntity
import com.capstone.foodcy.data.firebase.Auth

class CarouselViewModel : ViewModel() {
    private lateinit var slides: List<SlideEntity>
    private val auth = Auth()

    fun getAllSlides() : List<SlideEntity> {
        slides = Slides.getSlides()
        return slides
    }

    fun getIsLogin(activity: Activity) = auth.isLogin(activity)
}