package com.capstone.foodcy.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.capstone.foodcy.R
import com.capstone.foodcy.databinding.FragmentHomeBinding
import com.capstone.foodcy.ui.favorite.FavoriteFragment
import com.capstone.foodcy.ui.feed.FeedFragment
import com.capstone.foodcy.ui.recommendation.RecommendationFragment

class HomeFragment : Fragment(){

    companion object {

        @JvmStatic
        fun newInstance(isMyBoolean: Boolean) = HomeFragment().apply {
            arguments = Bundle().apply {
                putBoolean("LOADRECOM", isMyBoolean)
            }
        }
    }

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private var isMyBoolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feedFragment = FeedFragment()
        val recommendationFragment = RecommendationFragment()
        val favoriteFragment = FavoriteFragment()

        if (isMyBoolean) {
            setFragment(recommendationFragment)
        } else {
            setFragment(feedFragment)
        }

        fragmentHomeBinding.toolbar.inflateMenu(R.menu.home_menu)
        fragmentHomeBinding.toolbar.setOnMenuItemClickListener {
            when(it?.itemId) {
                R.id.recommendation -> setFragment(recommendationFragment)
                R.id.favorite -> setFragment(favoriteFragment)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) = childFragmentManager
        .beginTransaction()
        .apply {
            replace(R.id.fl_home_wrapper, fragment)
            commit()
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getBoolean("LOADRECOM")?.let {
            isMyBoolean = it
        }
    }

}