package com.capstone.foodcy.ui.recommendation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.databinding.ItemFoodBinding
import com.capstone.foodcy.ui.detail.DetailFoodActivity
import java.util.ArrayList

class RecommendationAdapter(private val list: List<FoodEntity>) : RecyclerView.Adapter<RecommendationAdapter.RecomViewHolder>(){
    class RecomViewHolder (private val binding: ItemFoodBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : FoodEntity) {
            with(binding) {
                Glide.with(context)
                    .load(item.image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .override(200,200)
                    .error(R.drawable.ic_broken_image)
                    .into(imgFood)

                tvName.text = item.name

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_FOOD, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomViewHolder {
        val itemFoodBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecomViewHolder(itemFoodBinding, parent.context)
    }

    override fun onBindViewHolder(holder: RecomViewHolder, position: Int) {
        val food = list[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = list.size
}