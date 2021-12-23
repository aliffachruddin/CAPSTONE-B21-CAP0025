package com.capstone.foodcy.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.databinding.FragmentFavoriteBinding
import com.capstone.foodcy.databinding.FragmentRecommendationBinding
import com.capstone.foodcy.ui.recommendation.RecommendationAdapter
import com.capstone.foodcy.ui.recommendation.RecommendationViewModel
import com.capstone.foodcy.utils.SpacesItemDecoration

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var viewModel : FavoriteViewModel
    private lateinit var listFood : List<FoodEntity>
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(FavoriteViewModel::class.java)

        loadData()

    }

    private fun loadData() {
        showNull(false)

        viewModel.getUserFavorit(activity!!.application).observe(viewLifecycleOwner, { uf ->
            val listId = ArrayList<String>()
            for (item in uf) {
                listId.add(item.idFood)
            }

            listFood = viewModel.getAllFavFood(requireActivity(), listId)

            if (listFood.isEmpty()) {
                showNull(true)
            }
            adapter = FavoriteAdapter(listFood)

            showLoading(true)

            with(binding) {
                rvFav.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                rvFav.adapter = adapter
            }
            showLoading(false)
        })

    }

    private fun showLoading(isVisible : Boolean) {
        if (isVisible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNull(isNull : Boolean) {
        if (isNull) {
            binding.imgIllustrationNull.visibility = View.VISIBLE
        } else {
            binding.imgIllustrationNull.visibility = View.GONE
        }
    }

}