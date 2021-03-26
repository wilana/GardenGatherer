package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        })
    }

    override fun gardenSelected(garden: Garden) {
        TODO("Not yet implemented")
    }
}