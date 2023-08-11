package com.example.easyfood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.adapter.CategoriesAdapter
import com.example.easyfood.adapter.FavoritesMealsAdapter
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
lateinit var binding: FragmentSearchBinding
lateinit var  viewModel:HomeViewModel
lateinit var  adapter: FavoritesMealsAdapter
var serchJob: Job? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)

        prepareRecyclerView()

        //auto search
        binding.boxSearch.addTextChangedListener {
            serchJob?.cancel()
            serchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(it.toString())
            }
        }
        //Search click arrow button
        binding.imgSearch.setOnClickListener {
            imageclick()
        }

        observeSearchMealsLiveData()
        return binding.root
    }

    private fun observeSearchMealsLiveData() {
       viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner){
           adapter.differ.submitList(it)
       }
    }

    private fun imageclick() {
        val searchQuery= binding.boxSearch.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        adapter = FavoritesMealsAdapter(requireContext())
        binding.searchRcl.layoutManager =GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        binding.searchRcl.adapter =adapter
    }
}