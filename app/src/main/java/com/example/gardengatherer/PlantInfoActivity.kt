package com.example.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gardengatherer.databinding.ActivityPlantInfoBinding


class PlantInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fill spinner with Annual, Perennial, Biannual
    }
}