package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager


import com.WSM1120464.gardengatherer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GardenViewAdapter.GardenItemListener {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add gardens
        val model : GardenViewModel by viewModels()
        model.getGardens().observe(this, Observer<List<Garden>>{ gardens ->
            var recyclerAdapter = GardenViewAdapter(this, gardens, this)
            binding.recyclerViewGardens.adapter = recyclerAdapter

        })

        // FAB changes activity to add a new garden
        binding.addGardenFAB.setOnClickListener {
            val intent = Intent(this, GardenSaveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun gardenSelected(garden: Garden) {
        val intent = Intent(this, PlantsActivity::class.java)
        intent.putExtra("gardenID", garden.gardenID)
        intent.putExtra("gardenName", garden.gardenName)
        startActivity(intent)
    }
}