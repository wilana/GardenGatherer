package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.WSM1120464.gardengatherer.databinding.ActivityPlantsBinding

class PlantsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}