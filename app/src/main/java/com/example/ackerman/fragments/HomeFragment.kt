package com.example.ackerman.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ackerman.R
import com.example.ackerman.activity.MealActivity
import com.example.ackerman.adapter.MostPopularAdapter
import com.example.ackerman.databinding.FragmentHomeBinding
import com.example.ackerman.databinding.PopularItemsBinding
import com.example.ackerman.pojo.CategoryMeals
import com.example.ackerman.pojo.Meal
import com.example.ackerman.pojo.MealList
import com.example.ackerman.retrofit.RetrofitInstance
import com.example.ackerman.videoModel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    companion object{
        const val MEAL_ID = "com.example.ackerman.fragments.idMeal"
        const val MEAL_NAME = "com.example.ackerman.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.ackerman.fragments.thumbMeal" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularItems()
        observerPopularItemsLivedata()
        preparePopularItemsRecyclerView()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularItemsLivedata() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner,
         { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
             this.randomMeal =meal
        })
    }



}


