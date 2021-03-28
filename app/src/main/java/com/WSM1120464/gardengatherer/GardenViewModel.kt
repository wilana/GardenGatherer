package com.WSM1120464.gardengatherer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Gets list of gardens from db
 */
class GardenViewModel : ViewModel() {
    private val gardens = MutableLiveData<List<Garden>>()

    init {
        loadGardens()
    }

    fun getGardens() : LiveData<List<Garden>> {
        return gardens
    }

    private fun loadGardens() {
        //query the firestore database for the gardens
        val db = FirebaseFirestore.getInstance().collection("gardens")
            .orderBy("gardenName", Query.Direction.ASCENDING)

        db.addSnapshotListener{ documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")

            if (exception != null){
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }



            documents?.let{
                val gardenList = ArrayList<Garden>()
                for(document in documents)
                {
                    val garden = document.toObject(Garden::class.java)
                    gardenList.add(garden)
                    garden.gardenName?.let { it1 -> Log.i("ADDED:", it1) }
                }
                gardens.value = gardenList
            }
        }
    }

}