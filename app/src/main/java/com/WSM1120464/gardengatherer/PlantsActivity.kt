package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.WSM1120464.gardengatherer.databinding.ActivityPlantsBinding

class PlantsActivity : AppCompatActivity(), PlantViewAdapter.PlantItemListener {
    private lateinit var binding: ActivityPlantsBinding

    private lateinit var viewModel : PlantViewModel
    private lateinit var viewModelFactory: PlantViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // update textview heading as name of garden selected
        binding.textViewPlantsGardenName.text = intent.getStringExtra("gardenName")
        val userID = intent.getStringExtra("userID")
        val gardenID = intent.getStringExtra("gardenID")

        // fill plants from recycler
        gardenID?.let{
            viewModelFactory = PlantViewModelFactory(gardenID)

            // connect view model with activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(PlantViewModel::class.java)
            viewModel.getPlants().observe(this, Observer<List<Plant>> { plants ->
                var recyclerAdapter = PlantViewAdapter(this, plants, this)
                binding.recyclerViewPlants.adapter = recyclerAdapter
            })
        }

        binding.extendedFabBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }

        binding.fabAddPlant.setOnClickListener {
            val intent = Intent(this, PlantSaveActivity::class.java)
            intent.putExtra("gardenID", gardenID)
            startActivity(intent)
        }
    }

    override fun plantSelected (plant : Plant) {
        val intent = Intent(this, IndividualPlantActivity::class.java)
        intent.putExtra("plantID", plant.plantID)
        startActivity(intent)
    }
}