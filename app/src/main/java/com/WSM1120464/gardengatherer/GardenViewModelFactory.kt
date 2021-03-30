package com.WSM1120464.gardengatherer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GardenViewModelFactory (private val userID : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenViewModel::class.java)) {
            return GardenViewModel(userID) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
