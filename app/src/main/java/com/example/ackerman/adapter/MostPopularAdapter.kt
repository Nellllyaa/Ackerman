package com.example.ackerman.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ackerman.databinding.PopularItemsBinding
import com.example.ackerman.pojo.CategoryList
import com.example.ackerman.pojo.CategoryMeals
import com.example.ackerman.pojo.MealList
import java.util.Locale.Category

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((CategoryMeals)->Unit)
    private var mealsList = ArrayList<CategoryMeals>()
    fun setMeals (mealsList: ArrayList<CategoryMeals>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopMI)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
    class PopularMealViewHolder (var binding: PopularItemsBinding): RecyclerView.ViewHolder(binding.root)
}