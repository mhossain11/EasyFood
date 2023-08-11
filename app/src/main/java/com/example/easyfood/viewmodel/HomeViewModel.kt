package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.model.Meal
import com.example.easyfood.model.MealModel
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.category.CategoryList
import com.example.easyfood.model.popularcategory.CategoryMeal
import com.example.easyfood.model.popularcategory.CategoryModel
import com.example.easyfood.service.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val mealDatabase: MealDatabase):ViewModel() {

    var randomMealLiveData = MutableLiveData<Meal>()
    var popularItemsLiveData = MutableLiveData<List<CategoryMeal>>()
    var categoriesLiveData = MutableLiveData<List<Category>>()
    var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    var bottomSheetMealLiveData =MutableLiveData<Meal>()
    private val searchMealsLiveData =MutableLiveData<List<Meal>>()
    //not reload landscape (horizontal) screen
    var saveSateRandomMeal:Meal?=null


   fun fetchData() {
       saveSateRandomMeal?.let {
           randomMealLiveData.postValue(it)
           return
       }
        RetrofitInstance.retrofit.getData().enqueue(object : Callback<MealModel?> {
            override fun onResponse(call: Call<MealModel?>, response: Response<MealModel?>) {
                if (response.body() != null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    randomMealLiveData.value =randomMeal
                    saveSateRandomMeal =randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealModel?>, t: Throwable) {
                Log.e("Image Error", t.localizedMessage.toString(), )
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.retrofit.getPopularItems("Seafood").enqueue(object : Callback<CategoryModel?> {
            override fun onResponse(
                call: Call<CategoryModel?>,
                response: Response<CategoryModel?>
            ) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryModel?>, t: Throwable) {
                Log.e("popularItems Error", t.localizedMessage.toString(), )
            }
        })


    }

    fun  getCategories(){
        RetrofitInstance.retrofit.getCategories().enqueue(object : Callback<CategoryList?> {
            override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {
                if (response.body() != null){
                    categoriesLiveData.postValue(response.body()!!.categories)


                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
                Log.e("popularItems Error", t.localizedMessage.toString(), )
            }
        })
    }
    fun getMealById(id:String){
        RetrofitInstance.retrofit.getMeal(id).enqueue(object : Callback<MealModel?> {
            override fun onResponse(call: Call<MealModel?>, response: Response<MealModel?>) {
                val meals = response.body()?.meals?.first()

                meals?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealModel?>, t: Throwable) {
                Log.e("BottomSheet_Error", t.localizedMessage.toString(), )
            }
        })
    }

    fun searchMeals(searchQuery:String){
        RetrofitInstance.retrofit.searchMeals(searchQuery).enqueue(object : Callback<MealModel?> {
            override fun onResponse(call: Call<MealModel?>, response: Response<MealModel?>) {
                val mealList=response.body()!!.meals

                mealList.let {
                    searchMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealModel?>, t: Throwable) {
                Log.e("SEARCHMEALS_Error", t.localizedMessage.toString(), )
            }
        })
    }

    fun observeFetchLivedata():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLivedata():LiveData<List<CategoryMeal>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }

    fun observeBottomSheetMealsLiveData():LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    fun observeSearchMealsLiveData():LiveData<List<Meal>>{
        return searchMealsLiveData
    }



    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }

}