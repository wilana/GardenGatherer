package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.WSM1120464.gardengatherer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerGardenAdapter.GardenItemListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : GardensViewModel by viewModels()
        model.getGardens().observe(this, Observer{ gardens ->
            var recyclerAdapter = RecyclerGardenAdapter(this, gardens, this)
            binding.verticalRecyclerView.adapter = recyclerAdapter
        })

        binding.addGardenFAB.setOnClickListener {
            val intent = Intent(this, GardenInfoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun gardenSelected(garden: Garden) {
        val intent = Intent(this, PlantInfoActivity::class.java)
    }
}