package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.WSM1120464.gardengatherer.databinding.ActivityPlantSaveBinding
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.firebase.firestore.FirebaseFirestore


class PlantSaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.extendedFabAllPlants.setOnClickListener {
            finish()
        }

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
                        finish()
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
}