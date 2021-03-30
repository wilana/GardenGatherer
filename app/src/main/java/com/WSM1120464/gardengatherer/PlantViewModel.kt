package com.WSM1120464.gardengatherer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PlantViewModel (gardenID : String) : ViewModel() {
    private val plants = MutableLiveData<List<Plant>>()

    init {
        // get list of plants in the garden from db
        val db = FirebaseFirestore.getInstance().collection("plants")
            .whereEqualTo("gardenID", gardenID)
            .orderBy("plantName", Query.Direction.ASCENDING)

        db.addSnapshotListener { documents, exception ->
            if (exception != null) {
                Log.w("DBQUERY", "Listen failed")
                return@addSnapshotListener
            }
            documents?.let {
                val plantList = ArrayList<Plant>()
                for(document in documents)
                {
                    val plant = document.toObject(Plant::class.java)
                    plantList.add(plant)
                    plant.plantName?.let { it1 -> Log.i("ADDED:", it1) }
                }
                plants.value = plantList
            }
        }
    }

    fun getPlants() : LiveData<List<Plant>> {
        return plants
    }
}