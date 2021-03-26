package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.WSM1120464.gardengatherer.databinding.ActivityPlantInfoBinding


class PlantInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}