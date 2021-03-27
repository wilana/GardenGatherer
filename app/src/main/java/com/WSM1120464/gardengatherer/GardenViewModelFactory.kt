package com.WSM1120464.gardengatherer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GardenViewModelFactory(private val gardenID : String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardensViewModel::class.java))
        {
            return GardensViewModel() as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}