package com.capstone.foodcy.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.databinding.ItemListFoodBinding
import com.capstone.foodcy.ui.detail.DetailFoodActivity


class SearchAdapter(private val list: List<FoodEntity>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(private val binding: ItemListFoodBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : FoodEntity) {
            with(binding) {
                Glide.with(context)
                    .load(item.image)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itembinding = ItemListFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(itembinding, parent.context)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val food = list[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = list.size

}