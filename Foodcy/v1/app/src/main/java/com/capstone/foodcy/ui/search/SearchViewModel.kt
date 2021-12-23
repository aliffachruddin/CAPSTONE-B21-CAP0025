package com.capstone.foodcy.ui.search

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.capstone.foodcy.data.csv.FoodcyCsv
import com.capstone.foodcy.data.entity.FoodEntity

class SearchViewModel : ViewModel() {

    private val foodcyCsv = FoodcyCsv()

    fun searchFood(activity: Activity, keyword: String) : List<FoodEntity> {
        return foodcyCsv.searchFood(keyword,activity)
    }

}