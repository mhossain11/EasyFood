package com.example.easyfood.fragment.bottomsheet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.easyfood.activity.MainActivity
import com.example.easyfood.activity.MealActivity
import com.example.easyfood.databinding.FragmentMealBottomSheetBinding
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentMealBottomSheetBinding
    lateinit var viewModel: HomeViewModel
    private var mealId:String? =null
    private var mealName:String?=null
    private var mealThumb:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        arguments?.let {
            mealId =it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetMeal()

        onBottomSheetDialogClick()

    }


    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName !=null && mealThumb !=null){
                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealsLiveData().observe(requireActivity()){
            if (context != null) {
                Glide.with(requireContext()).load(it.strMealThumb).into(binding.imgCategory)
            }
            binding.tvAreaInfo.text=it.strArea
            binding.tvAreatocat.text=it.strCategory
            binding.btmsheetMealName.text=it.strMeal

            mealName =it.strMeal
            mealThumb=it.strMealThumb
        }

    }

    companion object{
        @JvmStatic
        fun newInstance(param1:String)=MealBottomSheetFragment().apply {
             arguments = Bundle().apply {
                putString(MEAL_ID, param1)
            }
        }
    }
}