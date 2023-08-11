package com.example.easyfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.model.popularcategory.CategoryMeal

class MostPopularAdapter(val context: Context):RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {
    lateinit var onItemClick:((CategoryMeal) -> Unit)
    private var mealslist =ArrayList<CategoryMeal>()
    var onLongitemClick:((CategoryMeal)-> Unit)?=null

    fun setMeals(mealsLists :ArrayList<CategoryMeal>){
        this.mealslist =mealsLists
        notifyDataSetChanged()
    }

    class MostPopularViewHolder(var binding: PopularItemsBinding):ViewHolder(binding.root) {
        //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        val binding =PopularItemsBinding.inflate(LayoutInflater.from(context),parent,false)
        return MostPopularViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return  mealslist.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val data = mealslist[position]
        Glide.with(context).load(data.strMealThumb).into(holder.binding.imgPopularMeal)

        holder.binding.itemview.setOnClickListener {
            onItemClick.invoke(data)
        }

        holder.binding.itemview.setOnLongClickListener {
            onLongitemClick?.invoke(data)
            true

        }
    }
}