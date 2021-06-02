package com.capstone.foodcy.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.capstone.foodcy.R
import com.capstone.foodcy.ui.carousel.CarouselActivity

class SplashScreen : AppCompatActivity() {
    private val delay: Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(mainLooper).postDelayed({
            val intentToCarousel = Intent(this, CarouselActivity::class.java)
            startActivity(intentToCarousel)
            finish()
        }, delay.toLong())
    }
}
