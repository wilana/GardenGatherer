package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.WSM1120464.gardengatherer.databinding.ActivityPlantSaveBinding
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Add a plant to the garden
 */
class PlantSaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSaveBinding
    private val authDb = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up toolbar
        setSupportActionBar(binding.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get garden to add to
        val userID = intent.getStringExtra("userID")
        val gardenID = intent.getStringExtra("gardenID")


        // update labels when range slider is touched
        binding.rangeSliderBloomTime.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }
        })

        // update Labels for range slider with month as string
        binding.rangeSliderBloomTime.setLabelFormatter { value ->
            getMonth(value.toInt())
        }

        // Back button as FAB
//        binding.extendedFabAllPlants.setOnClickListener {
//            finish()
//        }

        binding.extendedFabSavePlant.setOnClickListener {
            // Plant validation only requires name
            if (binding.editTextPlantName.text.isNotEmpty()) {
                val plant = Plant()
                plant.plantName = binding.editTextPlantName.text.toString()
                plant.plantType = binding.spinnerType.selectedItem.toString()
                plant.plantLight = binding.spinnerPlantLight.selectedItem.toString()
                plant.plantHeight = binding.editTextPlantHeight.text.toString()
                plant.plantFertilizer = binding.editTextFertilizer.text.toString()
                plant.plantPruning = binding.editTextPruning.text.toString()
                plant.plantNotes = binding.editTextPlantNotes.text.toString()
                plant.gardenID = gardenID

                val values = binding.rangeSliderBloomTime.values
                plant.plantBloomStart = values[0].toInt()
                plant.plantBloomEnd = values[1].toInt()

                // connect to db
                val db = FirebaseFirestore.getInstance().collection("plants")
                plant.plantID = db.document().id

                // add garden to db
                db.document(plant.plantID!!).set(plant)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Plant Added", Toast.LENGTH_LONG).show()
                        // change to activity
                        val intent = Intent (this, PlantsActivity::class.java)
                        intent.putExtra("userID", userID)
                        intent.putExtra("gardenID", gardenID)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }

            }
            else
                Toast.makeText(this, "Please add the plant name", Toast.LENGTH_LONG).show()
            }
        }


    /**
     * Update textViews for both months
     */
    private fun updateMonths(values: MutableList<Float>) {
        binding.textViewFirstBloomMonth.text = getMonth(values[0].toInt())
        binding.textViewLastBloomMonth.text = getMonth(values[1].toInt())
    }

    /**
     * Get month in string form from an int
     */
    private fun getMonth(value: Int): String {
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

    // back button in toolbar
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