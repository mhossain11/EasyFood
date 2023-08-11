package com.example.easyfood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapter.CategoryMealsAdapter
import com.example.easyfood.databinding.ActivityCategoryMealBinding
import com.example.easyfood.fragment.CategoryFragment
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.model.Meal
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.popularcategory.CategoryMeal
import com.example.easyfood.viewmodel.CategoryMealViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var categoryMealViewModel: CategoryMealViewModel
    lateinit var binding: ActivityCategoryMealBinding
    lateinit var categoryName:String
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    companion object{
        const val MEAL_ID="Id"
        const val MEAL_NAME="Name"
        const val MEAL_THUMB="thumb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName =intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!

        categoryMealview()
        categoryMealRecycler()
        onPopularItemClick()
        getMealCategoryFragmentFromIntent()


    }

    private fun categoryMealRecycler() {
        categoryMealsAdapter = CategoryMealsAdapter(this)
        binding.mealRecyclerview.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.mealRecyclerview.adapter=categoryMealsAdapter
    }

    private fun categoryMealview() {
        categoryMealViewModel = ViewModelProvider(this)[CategoryMealViewModel::class.java]
        categoryMealViewModel.getMealsCategory(categoryName)
        categoryMealViewModel.observeCategoryMealLivedata().observe(this){

            binding.tvCategoryCount.text=it.size.toString()
            categoryMealsAdapter.setMeals(it as ArrayList<CategoryMeal>)
        }
    }

    private fun onPopularItemClick() {
        categoryMealsAdapter.onItemClick = {
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_NAME,it.strMeal)
            intent.putExtra(MEAL_THUMB,it.strMealThumb)
            startActivity(intent)

        }
    }

    private fun getMealCategoryFragmentFromIntent() {

        categoryName =intent.getStringExtra(CategoryFragment.CATEGORY_NAME)!!

    }
}