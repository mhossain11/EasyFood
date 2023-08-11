package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.model.popularcategory.CategoryMeal
import com.example.easyfood.model.popularcategory.CategoryModel
import com.example.easyfood.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel :ViewModel(){

    var categoryMealLiveData = MutableLiveData<List<CategoryMeal>>()

    fun getMealsCategory(categoryName:String){
        RetrofitInstance.retrofit.getMealsByCategory(categoryName).enqueue(object : Callback<CategoryModel?> {
            override fun onResponse(
                call: Call<CategoryModel?>,
                response: Response<CategoryModel?>
            ) {
                categoryMealLiveData.postValue(response.body()!!.meals)
            }

            override fun onFailure(call: Call<CategoryModel?>, t: Throwable) {
                Log.e("CategoryMeal_ERROR", t.localizedMessage.toString() )
            }
        })
    }

    fun observeCategoryMealLivedata(): LiveData<List<CategoryMeal>> {
        return categoryMealLiveData
    }
}