package com.example.easyfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.databinding.CatmealItemBinding
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.popularcategory.CategoryMeal

class CategoryMealsAdapter(val context: Context):RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private var catMealsList =ArrayList<CategoryMeal>()
    var onItemClick:((CategoryMeal)-> Unit)?=null

    fun setMeals(catMealsList :ArrayList<CategoryMeal>){
        this. catMealsList =catMealsList
        notifyDataSetChanged()
    }
    class CategoryMealsViewHolder(var binding: CatmealItemBinding):ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        var binding = CatmealItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return catMealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val data =catMealsList[position]
        Glide.with(context).load(data.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=data.strMeal

        holder.binding.catItem.setOnClickListener {
            onItemClick?.invoke(data)
            true

        }
    }
}