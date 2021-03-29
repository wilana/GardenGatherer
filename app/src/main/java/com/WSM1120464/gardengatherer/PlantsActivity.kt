package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.WSM1120464.gardengatherer.databinding.ActivityPlantsBinding

class PlantsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantsBinding

    private lateinit var viewModel : PlantViewModel
    private lateinit var viewModelFactory: PlantViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // update textview heading as name of garden selected
        binding.textViewPlantsGardenName.text = intent.getStringExtra("gardenName")
        val gardenID = intent.getStringExtra("gardenID")

        // fill plants from recycler
        gardenID?.let{
            viewModelFactory = PlantViewModelFactory(gardenID)

            // connect view model with activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(PlantViewModel::class.java)
            viewModel.getPlants().observe(this, Observer { plants ->
                var recyclerAdapter = PlantViewAdapter(this, plants)
                binding.recyclerViewPlants.adapter = recyclerAdapter
            })
        }

        binding.extendedFabBackToMain.setOnClickListener {
            finish()
        }

        binding.fabAddPlant.setOnClickListener {
            val intent = Intent(this, PlantSaveActivity::class.java)
            intent.putExtra("gardenID", gardenID)
            startActivity(intent)
        }
    }
}