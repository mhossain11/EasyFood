package com.example.easyfood.model.popularcategory


import com.google.gson.annotations.SerializedName

data class CategoryMeal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)