package com.example.ackerman.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ackerman.R
import com.example.ackerman.adapter.CategoryMealsAdapter
import com.example.ackerman.databinding.ActivityCategoryMealsBinding
import com.example.ackerman.databinding.ActivityMealBinding
import com.example.ackerman.fragments.HomeFragment
import com.example.ackerman.videoModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->

            categoryMealsAdapter.setMealsList(mealsList)
        })
    }
    private fun prepareRecyclerView(){
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.meals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}