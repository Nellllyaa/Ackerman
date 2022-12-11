package com.example.ackerman.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ackerman.R
import com.example.ackerman.activity.MainActivity
import com.example.ackerman.adapter.FavoriteMealsAdapter
import com.example.ackerman.databinding.FragmentFavoriteBinding
import com.example.ackerman.databinding.FragmentHomeBinding
import com.example.ackerman.videoModel.HomeViewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorite()
    }

    private fun prepareRecyclerView() {
        favoriteAdapter = FavoriteMealsAdapter()
        binding.resvFavorite.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorite() {
        viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer { meals->
            favoriteAdapter.differ.submitList(meals)
        })
    }


}