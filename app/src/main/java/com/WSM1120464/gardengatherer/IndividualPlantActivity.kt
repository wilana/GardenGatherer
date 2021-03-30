package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.WSM1120464.gardengatherer.databinding.ActivityIndividualPlantBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class IndividualPlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIndividualPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndividualPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantID = intent.getStringExtra("plantID")

        binding.textViewPlantFertilizer.movementMethod = ScrollingMovementMethod()
        binding.textViewPlantPruning.movementMethod = ScrollingMovementMethod()
        binding.textViewPlantNotes.movementMethod = ScrollingMovementMethod()

        binding.extendedFabBackToGarden.setOnClickListener {
            finish()
        }

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
}