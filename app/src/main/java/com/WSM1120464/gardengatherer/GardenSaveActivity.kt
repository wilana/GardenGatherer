package com.WSM1120464.gardengatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.WSM1120464.gardengatherer.databinding.ActivityGardenSaveBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

/**
 *
 */
class GardenSaveActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGardenSaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGardenSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.extendedFABallGardens.setOnClickListener {
            finish()
        }

        binding.extendedFabSaveGarden.setOnClickListener {
            // only validating garden and person's name
            if (binding.editTextTextGardenName.text.toString().isNotEmpty() && binding.editTextYourName.text.toString().isNotEmpty())
            {
                // save inputed info
                val garden = Garden()
                garden.gardenName = binding.editTextTextGardenName.text.toString()
                garden.userName = binding.editTextYourName.text.toString()
                garden.gardenSize = binding.editTextGardenSize.text.toString()
                garden.lightConditions = binding.spinnerGardenLight.selectedItem.toString()

                // connect to db
                val db = FirebaseFirestore.getInstance().collection("gardens")
                garden.gardenID = db.document().id

                // add garden to db
                db.document(garden.gardenID!!).set(garden)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Garden Added", Toast.LENGTH_LONG).show()
                        // change to activity
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }

            }
            else
                Toast.makeText(this, "Please add your name and the garden name", Toast.LENGTH_LONG).show()
        }
    }
}