package com.example.easyfood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMainBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.viewmodel.HomeViewModel
import com.example.easyfood.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    val viewModel:HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory =HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController =Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

    }
}