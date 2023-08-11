package com.example.easyfood.model.popularcategory


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("meals")
    val meals: List<CategoryMeal>
)