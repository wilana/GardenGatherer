package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.WSM1120464.gardengatherer.databinding.ActivityPlantsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * View list of plants in a garden
 */
class PlantsActivity : AppCompatActivity(), PlantViewAdapter.PlantItemListener {
    private lateinit var binding: ActivityPlantsBinding
    private val authDb = FirebaseAuth.getInstance()

    private lateinit var viewModel : PlantViewModel
    private lateinit var viewModelFactory: PlantViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup toolbar
        setSupportActionBar(binding.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get garden to look for
        val gardenID = intent.getStringExtra("gardenID")
        val gardenName = intent.getStringExtra("gardenName")
        // update textview heading as name of garden selected
        binding.topToolbar.title = gardenName
        binding.textViewGardenNotes.text = intent.getStringExtra("gardenNotes")



        // fill plants from recycler
        gardenID?.let{
            viewModelFactory = PlantViewModelFactory(gardenID)

            // connect view model with activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(PlantViewModel::class.java)
            viewModel.getPlants().observe(this, Observer<List<Plant>> { plants ->
                val recyclerAdapter = PlantViewAdapter(this, plants, this)
                binding.recyclerViewPlants.adapter = recyclerAdapter
            })
        }

        // back button as a FAB
//        binding.extendedFabBackToMain.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("userID", userID)
//            startActivity(intent)
//        }

        // switch activity to add a new plant
        binding.fabAddPlant.setOnClickListener {
            val intent = Intent(this, PlantSaveActivity::class.java)
            intent.putExtra("gardenID", gardenID)
            intent.putExtra("gardenName", gardenName)
            startActivity(intent)
        }
    }

    // view all plant information without editing
    override fun plantSelected (plant : Plant) {
        val intent = Intent(this, IndividualPlantActivity::class.java)
        intent.putExtra("plantID", plant.plantID)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
//            R.id.action_filter -> {
//                // User chose a filter option
//                return true
//            }
            R.id.action_logoff -> {
                authDb.signOut()
                finish()
                startActivity(Intent(this, SignInActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}