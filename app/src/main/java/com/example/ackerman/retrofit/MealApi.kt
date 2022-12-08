package com.example.ackerman.retrofit


import com.example.ackerman.pojo.CategoryList
import com.example.ackerman.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.IDN

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String): Call<CategoryList>
}


