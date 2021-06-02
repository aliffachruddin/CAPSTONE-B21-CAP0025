package com.capstone.foodcy.ui.carousel

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.capstone.foodcy.data.entity.SlideEntity
import com.capstone.foodcy.databinding.ItemSlideBinding
import com.capstone.foodcy.ui.register.RegisterActivity


class CarouselAdapter(private var slides: List<SlideEntity>, private val context: Context, private val config: Int)
    : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemSlideBinding = ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(itemSlideBinding, context, config)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val slide = slides[position]
        holder.bind(slide)
    }

    override fun getItemCount(): Int = slides.size

    class CarouselViewHolder(private val binding: ItemSlideBinding, private val context: Context, private val config: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slide: SlideEntity) {
            with(binding) {
                tvTitle.text = slide.title
                imgIllustration.setImageResource(slide.illustration)
                tvCaption.text = slide.caption

                if (slide.isButtonShown) {
                    btnGetStarted.visibility = VISIBLE
                    btnGetStarted.setOnClickListener {
                        moveActivity()
                    }
                } else {
                    btnGetStarted.visibility = GONE
                }

                if (config == Configuration.ORIENTATION_PORTRAIT) {
                    itemContainer.orientation = LinearLayout.VERTICAL
                } else {
                    itemContainer.orientation = LinearLayout.HORIZONTAL
                    itemContainer.setVerticalGravity(16)
                }
            }
        }

        private fun moveActivity() {
            val intentToMain = Intent(context.applicationContext, RegisterActivity::class.java)
            context.startActivity(intentToMain)
        }

    }
}