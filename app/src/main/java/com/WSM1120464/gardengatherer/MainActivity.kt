package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.WSM1120464.gardengatherer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * View all of user's gardens
 */
class MainActivity : AppCompatActivity(), GardenViewAdapter.GardenItemListener {
    private lateinit var binding: ActivityMainBinding
    private val authDb = FirebaseAuth.getInstance()

    private lateinit var viewModel : GardenViewModel
    private lateinit var viewModelFactory: GardenViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up toolbar with no back option
        setSupportActionBar(binding.topToolbar)


        // get user to query for gardens
        val userID = intent.getStringExtra("userID")

        // fill gardens from user's gardens
        userID?.let{
            viewModelFactory = GardenViewModelFactory(userID)
            // connect view model with activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(GardenViewModel::class.java)
            viewModel.getGardens().observe(this, Observer<List<Garden>>{ gardens ->
                var recyclerAdapter = GardenViewAdapter(this, gardens, this)
                binding.recyclerViewGardens.adapter = recyclerAdapter

            })
        }

        // FAB changes activity to add a new garden
        binding.addGardenFAB.setOnClickListener {
            val intent = Intent(this, GardenSaveActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }

    }

    /**
     * Switch to List of Plants for the selected garden
     * Send userID and gardenID for querying
     * Send gardenName and gardenNotes for viewing
     */
    override fun gardenSelected(garden: Garden) {
        val intent = Intent(this, PlantsActivity::class.java)
        intent.putExtra("userID", garden.userID)
        intent.putExtra("gardenID", garden.gardenID)
        intent.putExtra("gardenName", garden.gardenName)
        intent.putExtra("gardenNotes", garden.gardenNotes)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
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