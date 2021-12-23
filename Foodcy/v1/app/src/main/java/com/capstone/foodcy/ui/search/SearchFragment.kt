package com.capstone.foodcy.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.foodcy.R
import com.capstone.foodcy.databinding.FragmentSearchBinding
import com.capstone.foodcy.databinding.FragmentSettingBinding
import com.capstone.foodcy.ui.setting.SettingViewModel
import com.capstone.foodcy.utils.`object`.Utils

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(SearchViewModel::class.java)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Utils.hideSoftKeyBoard(requireContext(), view)
                binding.imgSearching.visibility = View.GONE
                val result = viewModel.searchFood(requireActivity(), query.toString())
                if (result.isNotEmpty()) {
                    adapter = SearchAdapter(result)
                    with(binding) {
                        rvSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        rvSearch.adapter = adapter
                    }
                } else {
                    binding.imgSearching.visibility = View.VISIBLE
                    Toast.makeText(context, "No Match found", Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

}