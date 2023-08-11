package com.example.easyfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.model.category.Category
import com.example.easyfood.model.popularcategory.CategoryMeal

class CategoriesAdapter(val context: Context):RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> (){

    private var categorieslist =ArrayList<Category>()
    var onItemClick:((Category)-> Unit)?=null

    fun setMeals(categoriesLists :ArrayList<Category>){
        this. categorieslist =categoriesLists
        notifyDataSetChanged()
    }
    class CategoriesViewHolder(var binding: CategoryItemBinding):ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        var binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categorieslist.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val data =categorieslist[position]

        Glide.with(context).load(data.strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=data.strCategory

        holder.binding.cardItem.setOnClickListener {
            onItemClick?.invoke(data)
        }

    }
}