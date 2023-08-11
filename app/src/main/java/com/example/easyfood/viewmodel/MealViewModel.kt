package com.example.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.model.Meal
import com.example.easyfood.model.MealModel
import com.example.easyfood.service.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase):ViewModel() {
    var mealDetailsliveData=MutableLiveData<Meal>()

    fun mealDetails(id:String){
        RetrofitInstance.retrofit.getMeal(id).enqueue(object : Callback<MealModel?> {
            override fun onResponse(call: Call<MealModel?>, response: Response<MealModel?>) {
                if (response.body()!=null){
                    mealDetailsliveData.value =response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealModel?>, t: Throwable) {
                Log.e("MealDetails_ERROR", t.localizedMessage.toString(), )
            }
        })
    }
    fun observeMealliveData():LiveData<Meal>{
        return mealDetailsliveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }


}