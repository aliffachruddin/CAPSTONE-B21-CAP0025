package com.capstone.foodcy.ui.feed

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.foodcy.R
import com.capstone.foodcy.data.csv.FoodcyCsv
import com.capstone.foodcy.data.entity.FoodEntity
import com.capstone.foodcy.databinding.FragmentFeedBinding
import com.capstone.foodcy.ui.favorite.FavoriteAdapter
import com.capstone.foodcy.utils.SpacesItemDecoration
import com.google.firebase.database.DatabaseReference

class FeedFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(FeedViewModel::class.java)

        binding.tvHi.text = resources.getString(R.string.hi, viewModel.user?.displayName)

        val config = resources.configuration
        onConfigurationChanged(config)

        isUserVerified()



        binding.btnVerify.setOnClickListener(this)
    }

    private fun isUserVerified(){
        if (viewModel.getIsVerified()) {
            binding.containerVerification.visibility = View.GONE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.containerVerification.orientation = LinearLayout.VERTICAL
        } else {
            binding.containerVerification.orientation = LinearLayout.HORIZONTAL
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_verify -> {
                viewModel.setVerify(requireActivity())
            }
        }
    }

}