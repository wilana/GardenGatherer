package com.example.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.gardengatherer.databinding.ActivityPlantInfoBinding


class PlantInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fill spinner from string array
        val plantTypes = resources.getStringArray(R.array.plantTypes)
        if (binding.spinnerType != null)
        {
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    plantTypes)
            binding.spinnerType.adapter = adapter
        }
    }
}