package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activity.CategoryMealActivity
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.adapter.CategoriesAdapter
import com.example.easyfood.databinding.FragmentCategoryBinding
import com.example.easyfood.model.category.Category
import com.example.easyfood.viewmodel.HomeViewModel

class CategoryFragment : Fragment() {
lateinit var binding :FragmentCategoryBinding
lateinit var adapter:CategoriesAdapter
lateinit var viewModel:HomeViewModel

    companion object{
        const val CATEGORY_NAME="category"
    }
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryBinding.inflate(layoutInflater)
        viewModel=(activity as MainActivity).viewModel

        preparRecycleview()

        observeCategories()

        onCatItemClick()

        return binding.root
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){
            adapter.setMeals(it as ArrayList<Category>)
        }
    }

    private fun preparRecycleview() {
        adapter = CategoriesAdapter(requireContext())
        binding.rcl.layoutManager =GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        binding.rcl.adapter=adapter
    }

    private fun onCatItemClick() {
        adapter.onItemClick = {
            val intent = Intent(activity, CategoryMealActivity::class.java)
             intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)

        }
    }
}