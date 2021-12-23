package com.capstone.foodcy.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.foodcy.R
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_FOOD = "extra_food"
    }

    private lateinit var binding : ActivityDetailFoodBinding
    private lateinit var viewModel: DetailFoodViewModel
    private var idFood = ""
    private var foodName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(DetailFoodViewModel::class.java)

        showLoading(true)

        val food = intent.getParcelableExtra<FoodEntity>(EXTRA_FOOD) as FoodEntity
        idFood = food.id
        foodName = food.name

        checkUserFavorited(idFood)

        with(binding) {
            Glide.with(this@DetailFoodActivity)
                .load(food.image)
                .error(R.drawable.ic_broken_image)
                .into(imgFood)

            tvName.text = food.name
            tvPrepTime.text = resources.getString(R.string.value_minutes, food.prep_time)
            tvCookTime.text = resources.getString(R.string.value_minutes, food.cook_time)
            tvIngredients.text = food.ingredients
            tvCalories.text = resources.getString(R.string.value_calories, food.calories)
            tvDiet.text = food.diet
            tvDiebetes.text = if (food.diabetic == "1") "No" else "Yes"
            tvFlavor.text = food.flavor
            tvCourse.text = food.course
            tvHalal.text = if (food.halal == "1") "Yes" else "No"

            showLoading(false)
        }

        binding.tbFav.setOnClickListener(this)
        binding.btnLinkRecipes.setOnClickListener(this)
    }

    private fun showLoading(isVisible : Boolean) {
        if (isVisible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun settingFavButton(isActive : Boolean){
        binding.tbFav.isChecked = isActive
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tb_fav -> {
                if (binding.tbFav.isChecked) {
                    addToFavorite(idFood)
                } else {
                    removeFromFavorite(idFood)
                }
            }
            R.id.btn_link_recipes -> {
                val url = resources.getString(R.string.link_recipe_values, foodName)
                val openBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(openBrowser)
            }
        }
    }

    private fun checkUserFavorited(idFood: String) {
        viewModel.getUserFavoritedbyId(application, idFood).observe(this, { uf ->
            for (item in uf) {
                if (item.idFood.isNotEmpty()) {
                    settingFavButton(true)
                }
            }
        })
    }

    private fun removeFromFavorite(idFood: String) {
        val user = viewModel.user?.uid.toString()
        val item = RoomUserFavorit(user, idFood)
        viewModel.delete(item, application)
        Toast.makeText(this, "You just remove $foodName from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun addToFavorite(idFood: String) {
        val user = viewModel.user?.uid.toString()
        val item = RoomUserFavorit(user, idFood)
        viewModel.insert(item, application)
        Toast.makeText(this, "You just add $foodName to favorite", Toast.LENGTH_SHORT).show()
    }
}