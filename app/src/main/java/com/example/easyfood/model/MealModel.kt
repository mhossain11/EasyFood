package com.example.easyfood.model


import com.google.gson.annotations.SerializedName

data class MealModel(
    @SerializedName("meals")
    val meals: List<Meal>
)