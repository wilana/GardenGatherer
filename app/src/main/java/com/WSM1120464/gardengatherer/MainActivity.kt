package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.WSM1120464.gardengatherer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GardenViewAdapter.GardenItemListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel : GardenViewModel
    private lateinit var viewModelFactory: GardenViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userID = intent.getStringExtra("userID")
        // fill gardens from user's gardens
        userID?.let{
            viewModelFactory = GardenViewModelFactory(userID)
            // connect view model with activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(GardenViewModel::class.java)
            viewModel.getGardens().observe(this, Observer<List<Garden>>{ gardens ->
                var recyclerAdapter = GardenViewAdapter(this, gardens, this)
                binding.recyclerViewGardens.adapter = recyclerAdapter

            })
        }

        // FAB changes activity to add a new garden
        binding.addGardenFAB.setOnClickListener {
            val intent = Intent(this, GardenSaveActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
    }

    override fun gardenSelected(garden: Garden) {
        val intent = Intent(this, PlantsActivity::class.java)
        intent.putExtra("userID", garden.userID)
        intent.putExtra("gardenID", garden.gardenID)
        intent.putExtra("gardenName", garden.gardenName)
        intent.putExtra("gardenNotes", garden.gardenNotes)
        startActivity(intent)
    }
}