package com.capstone.foodcy.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.databinding.ActivityMainBinding
import com.capstone.foodcy.ui.home.HomeFragment
import com.capstone.foodcy.ui.profile.ProfileFragment
import com.capstone.foodcy.ui.search.SearchFragment
import com.capstone.foodcy.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()
        val settingFragment = SettingFragment()

        setCurrentFragment(homeFragment)

        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.search -> setCurrentFragment(searchFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
                R.id.setting -> setCurrentFragment(settingFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}