package com.WSM1120464.gardengatherer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Gets list of gardens from db
 */
class GardensViewModel : ViewModel() {
    private val gardens = MutableLiveData<List<Garden>>()

    init{
        //query the firestore database for the gardens
        val db = FirebaseFirestore.getInstance().collection("gardens")

        db.addSnapshotListener{ documents, exception ->
            if (exception != null){
                Log.w("DBQUERY", "Listen failed")
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

    fun getGardens() : LiveData<List<Garden>> {
        return gardens
    }
}