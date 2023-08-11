package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.activity.CategoryMealActivity
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.adapter.CategoriesAdapter
import com.example.easyfood.adapter.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.fragment.bottomsheet.MealBottomSheetFragment
import com.example.easyfood.model.Meal
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.popularcategory.CategoryMeal
import com.example.easyfood.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var randomMeal:Meal
    lateinit var popularAdapter: MostPopularAdapter
    lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID="Id"
        const val MEAL_NAME="Name"
        const val MEAL_THUMB="thumb"
        const val CATEGORY_NAME="category"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)


        dataFetch()
        onRendomMealClick()

        popularItemData()
        popularItemRecycleView()
        onPopularItemClick()

        observeCategoriesLiveData()
        CategoriesRecycleView()
        onCategoriesClick()

        onpopularitemLongClick()

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        return binding.root

    }
    //Problem
    //back button not  remove stack memory
    private fun onpopularitemLongClick() {
        popularAdapter.onLongitemClick ={
           val  mealBottomSheetFragment = MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"meal")

        }

    }

    private fun onCategoriesClick() {
        categoriesAdapter.onItemClick ={
            val intent =Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }

    }

    fun CategoriesRecycleView() {
        categoriesAdapter = CategoriesAdapter(requireContext())

        binding.categoriesRecyclerview.layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        binding.categoriesRecyclerview.adapter=categoriesAdapter

    }

    private fun observeCategoriesLiveData() {
        homeViewModel.getCategories()
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){
            categoriesAdapter.setMeals(it as ArrayList<Category>)
        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = {
            val intent =Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_NAME,it.strMeal)
            intent.putExtra(MEAL_THUMB,it.strMealThumb)
            startActivity(intent)

        }
    }

    private fun popularItemRecycleView() {
        popularAdapter = MostPopularAdapter(requireContext())
        binding.recViewMealsPopular.layoutManager =LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.recViewMealsPopular.adapter=popularAdapter
    }

    private fun popularItemData() {
        homeViewModel.getPopularItems()
        homeViewModel.observePopularItemsLivedata().observe(viewLifecycleOwner)
        {
            popularAdapter.setMeals(it as ArrayList<CategoryMeal>)

        }
    }

    private fun onRendomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    fun dataFetch(){
   homeViewModel =(activity as MainActivity).viewModel
    homeViewModel.fetchData()
    homeViewModel.observeFetchLivedata().observe(viewLifecycleOwner
    ) { value ->
        Glide.with(this@HomeFragment).load(value!!.strMealThumb).into(binding.imgRandomMeal)
        randomMeal = value
    }

}



}


