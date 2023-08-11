package com.example.easyfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.databinding.FavoriteItemBinding
import com.example.easyfood.model.Meal

class FavoritesMealsAdapter(val context: Context):RecyclerView.Adapter<FavoritesMealsAdapter.FavoritesMealsViewHolder>() {
  inner  class FavoritesMealsViewHolder(val binding:FavoriteItemBinding):ViewHolder(binding.root)

    private val diffUtil =object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
       val binding =FavoriteItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return FavoritesMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
       val data = differ.currentList[position]
        Glide.with(context).load(data.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=data.strMeal
    }


}