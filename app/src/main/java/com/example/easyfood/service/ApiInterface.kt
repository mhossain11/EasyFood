package com.example.easyfood.service


import com.example.easyfood.model.MealModel
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.category.CategoryList
import com.example.easyfood.model.popularcategory.CategoryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("random.php")
    fun getData(): Call<MealModel>

    @GET("lookup.php")
    fun getMeal(@Query("i") id:String):Call<MealModel>

    @GET("filter.php")
    fun getPopularItems(@Query("c")categoryName:String):Call<CategoryModel>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String):Call<CategoryModel>

    @GET("search.php")
   fun searchMeals(@Query("s") searchQuery:String):Call<MealModel>
}