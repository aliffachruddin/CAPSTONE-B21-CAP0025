package com.capstone.foodcy.ui.carousel

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.foodcy.data.entity.SlideEntity
import com.capstone.foodcy.databinding.ActivityCarouselBinding
import com.google.firebase.auth.FirebaseAuth

class CarouselActivity : AppCompatActivity() {

    private lateinit var carouselBinding: ActivityCarouselBinding
    private lateinit var slides: List<SlideEntity>
    private lateinit var viewModel: CarouselViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        carouselBinding = ActivityCarouselBinding.inflate(layoutInflater)
        setContentView(carouselBinding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CarouselViewModel::class.java)

        slides = viewModel.getAllSlides()

        loadSlides()
    }

    private fun loadSlides() {
        val config = resources.configuration.orientation

        adapter = CarouselAdapter(slides, this, config)
        with(carouselBinding) {
            rvCarousel.layoutManager = LinearLayoutManager(this@CarouselActivity, LinearLayoutManager.HORIZONTAL, false)
            rvCarousel.adapter = adapter
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adapter.notifyDataSetChanged()
        loadSlides()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getIsLogin(this)
    }
}