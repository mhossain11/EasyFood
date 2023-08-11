package com.example.easyfood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.adapter.FavoritesMealsAdapter
import com.example.easyfood.databinding.FragmentFavoriteBinding
import com.example.easyfood.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
lateinit var binding:FragmentFavoriteBinding
lateinit var viewModel: HomeViewModel
lateinit var favoriteAdapter: FavoritesMealsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position =viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoriteAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)

        return binding.root

    }

    private fun prepareRecyclerView() {
        favoriteAdapter =FavoritesMealsAdapter(requireContext())
        binding.favRecView.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        binding.favRecView.adapter=favoriteAdapter
    }

    private fun observeFavorites() {
         viewModel.observeFavoritesMealsLiveData().observe(requireActivity()){
            favoriteAdapter.differ.submitList(it)
         }
    }


}