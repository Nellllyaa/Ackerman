package com.example.ackerman.retrofit


import com.example.ackerman.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}