package com.example.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyfood.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun update(meal: Meal)

   @Delete
   suspend fun delete(meal: Meal)

   @Query("Select * From mealInformation")
   fun getAllMeals():LiveData<List<Meal>>
}
