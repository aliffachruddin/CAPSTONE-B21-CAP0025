package com.capstone.foodcy.ui.recommendation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.data.entity.UserCluster
import com.capstone.foodcy.databinding.FragmentRecommendationBinding
import com.capstone.foodcy.ui.favorite.FavoriteAdapter
import com.capstone.foodcy.ui.quiz.QuizActivity
import com.capstone.foodcy.utils.SpacesItemDecoration
import com.google.firebase.database.*

class RecommendationFragment : Fragment(){

    private lateinit var binding : FragmentRecommendationBinding
    private lateinit var viewModel : RecommendationViewModel
    private lateinit var adapter: RecommendationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecommendationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(RecommendationViewModel::class.java)

        showLoading(true)

        showData()

        /*
        ref = FirebaseDatabase.getInstance().reference
        ref.child("userCluster")
            .orderByChild("uid")
            .equalTo(viewModel.user?.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        listCluster = mutableListOf()
                        listCluster.clear()

                        for (s in snapshot.children) {
                            val cluster = s.getValue(UserCluster::class.java)
                            listCluster.add(cluster!!)
                        }

                        var cl = 0
                        for (c in listCluster) {
                            cl = c.cluster
                        }

                        val listFoods: List<FoodEntity> = viewModel.getListRecommendation(cl, requireActivity())

                        val adapter = RecommendationAdapter(listFoods)

                        with(binding) {
                            val decoration = SpacesItemDecoration(16)
                            rvRecom.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                            rvRecom.adapter = adapter
                            rvRecom.addItemDecoration(decoration)
                        }
                    } else {
                        showNull(true)
                    }

                    showLoading(false)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error Read Data", "loadPost:onCancelled, ${error.toException()}")
                }

            })

         */
    }

    private fun showData() {
        showNull(false)
        viewModel.getUserCluster(activity!!.application).observe(viewLifecycleOwner, { us ->
            if (us.isNotEmpty()) {
                var cluster = 0

                for (item in us) {
                    cluster = item.cluster.toString().toInt()
                }

                val listFoods: List<FoodEntity> = viewModel.getListRecommendation(cluster, requireActivity())

                adapter = RecommendationAdapter(listFoods)

                with(binding) {
                    val decoration = SpacesItemDecoration(16)
                    rvRecom.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    rvRecom.adapter = adapter
                    rvRecom.addItemDecoration(decoration)
                }

                showNull(false)
                showLoading(false)

            } else {
                showNull(true)
                showLoading(false)
            }
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
            binding.llNullRecom.visibility = View.VISIBLE
        } else {
            binding.llNullRecom.visibility = View.GONE
        }
    }
}