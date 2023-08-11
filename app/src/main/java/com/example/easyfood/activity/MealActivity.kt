package com.example.easyfood.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.fragment.CategoryFragment
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.model.Meal
import com.example.easyfood.viewmodel.MealViewModel
import com.example.easyfood.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMealBinding
   private lateinit var youtubeLink:String
   private lateinit var mealViewModel: MealViewModel
    private var mealToSave:Meal?=null
    private lateinit var mealid:String
    private lateinit var mealname:String
    private lateinit var mealthumb:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getMealInformationFromIntent()
        setInformationInViews()
        observeMealDeatilsLiveData()

        onYoutubeImageClick()

        onFavoriteClick()

        getMealCatInformationFromIntent()

       // getMealCategoryFragmentFromIntent()

    }

    private fun onFavoriteClick() {
       binding.btnSave.setOnClickListener {
           mealToSave?.let { it ->
               mealViewModel.insertMeal(it)
               Log.e("SAVE", it.toString(), )
               Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
           }
       }
    }

    private fun onYoutubeImageClick() {
       binding.imgYoutube.setOnClickListener {
           val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }


    private fun observeMealDeatilsLiveData() {
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory =MealViewModelFactory(mealDatabase)
        mealViewModel =ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        loadingCase()
        mealViewModel.mealDetails(mealid)
        mealViewModel.observeMealliveData().observe(this
        ) {
            mealToSave =it
            onResponseCase()
            binding.tvCategoryInfo.text="Category: ${it.strCategory}"
            binding.tvAreaInfo.text= "Area: ${it.strArea}"
            binding.tvContent.text= it.strInstructions
            youtubeLink =it.strYoutube!!

        }

    }

    private fun setInformationInViews() {
        Glide.with(this).load(mealthumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title =mealname
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {

        mealid = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealname = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealthumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun getMealCatInformationFromIntent() {

        mealid = intent.getStringExtra(CategoryMealActivity.MEAL_ID)!!
        mealname = intent.getStringExtra(CategoryMealActivity.MEAL_NAME)!!
        mealthumb = intent.getStringExtra(CategoryMealActivity.MEAL_THUMB)!!

    }


    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnSave.visibility=View.INVISIBLE
        binding.tvInstructions.visibility=View.INVISIBLE
        binding.tvCategoryInfo.visibility=View.INVISIBLE
        binding.tvAreaInfo.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE


    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnSave.visibility=View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.tvCategoryInfo.visibility=View.VISIBLE
        binding.tvAreaInfo.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE

    }
}