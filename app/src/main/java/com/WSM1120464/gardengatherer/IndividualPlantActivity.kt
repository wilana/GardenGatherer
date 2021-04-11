package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.WSM1120464.gardengatherer.databinding.ActivityIndividualPlantBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * View static plant information
 */
class IndividualPlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIndividualPlantBinding
    private val authDb = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndividualPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup toolbar
        setSupportActionBar(binding.mainToolBar.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get the plant to look for
        val plantID = intent.getStringExtra("plantID")

        // Add scrolling to larger text areas
        binding.textViewPlantFertilizer.movementMethod = ScrollingMovementMethod()
        binding.textViewPlantPruning.movementMethod = ScrollingMovementMethod()
        binding.textViewPlantNotes.movementMethod = ScrollingMovementMethod()

        // Back button as a FAB
//        binding.extendedFabBackToGarden.setOnClickListener {
//            finish()
//        }

        // get list of plants in the garden from db
        val db = FirebaseFirestore.getInstance().collection("plants")
            .whereEqualTo("plantID", plantID)

        db.get().addOnSuccessListener { documents ->
            for (document in documents)
            {
                //this will show our restaurants in the log
                Log.i("DB_RESPONSE", "${document.data}")

                //this will create an instance of the restaurant object
                val plant = document.toObject(Plant::class.java)

                plant?.let {
                    binding.textViewPlantName.text = plant.plantName
                    binding.textViewPlantType.text = plant.plantType
                    binding.textViewPlantLight.text = plant.plantLight
                    binding.textViewPlantHeight.text = plant.plantHeight
                    binding.textViewPlantBloomStart.text = getMonth(plant.plantBloomStart!!)
                    binding.textViewPlantBloomEnd.text = getMonth(plant.plantBloomEnd!!)
                    if(!plant.plantFertilizer.isNullOrBlank())
                        binding.textViewPlantFertilizer.text = plant.plantFertilizer
                    if (!plant.plantPruning.isNullOrBlank())
                        binding.textViewPlantPruning.text = plant.plantPruning
                    if (!plant.plantNotes.isNullOrBlank())
                        binding.textViewPlantNotes.text = plant.plantNotes

                }

            }
        }
    }

    /**
     * change month from int to string
     */
    fun getMonth(value : Int): String {
        return when (value) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Error"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return true
    }

    // set up back button in menu
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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