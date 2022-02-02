package com.capstone.foodcy.ui.feed

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.foodcy.R
import com.capstone.foodcy.databinding.FragmentFeedBinding

class FeedFragment : Fragment(){
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


        val food = viewModel.getFood(requireActivity())
        val adapter =  FeedAdapter(food)

        with(binding){
            rvFood.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvFood.adapter = adapter
        }

        val config = resources.configuration
        onConfigurationChanged(config)
    }


}