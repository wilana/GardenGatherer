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

        // Fill type spinner from string array
        val plantTypes = resources.getStringArray(R.array.plantTypes)
        if (binding.spinnerType != null)
        {
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    plantTypes)
            binding.spinnerType.adapter = adapter
        }

        // Fill light spinner from string array
        val plantLight = resources.getStringArray(R.array.lightTypes)
        if (binding.spinnerPlantLight != null)
        {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                plantLight)
            binding.spinnerPlantLight.adapter = adapter
        }
    }
}