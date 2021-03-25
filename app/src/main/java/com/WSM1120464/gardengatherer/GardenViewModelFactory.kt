package com.WSM1120464.gardengatherer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CommentViewModelFactory(private val gardenID : String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardensViewModel::class.java))
        {
            return GardensViewModel(gardenID) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}