package com.example.ackerman.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ackerman.R
import com.example.ackerman.databinding.ActivityMainBinding
import com.example.ackerman.databinding.ActivityMealBinding
import com.example.ackerman.fragments.HomeFragment
import com.example.ackerman.pojo.Meal
import com.example.ackerman.videoModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    private lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.youtube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                binding.category.text = " Category: ${meal!!.strCategory}"
                binding.area.text = "Area: ${meal.strArea}"
                binding.step.text = meal.strInstructions
                youtubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.black))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.black))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.category.visibility = View.INVISIBLE
        binding.area.visibility = View.INVISIBLE
        binding.youtube.visibility = View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.category.visibility = View.VISIBLE
        binding.area.visibility = View.VISIBLE
        binding.youtube.visibility = View.VISIBLE
    }
}