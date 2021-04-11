package com.WSM1120464.gardengatherer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Send gardenID to PlantViewModel for querying
 */
class PlantViewModelFactory(private val gardenID : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            return PlantViewModel(gardenID) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}