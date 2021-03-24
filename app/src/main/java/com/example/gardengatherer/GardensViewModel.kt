package com.example.gardengatherer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GardensViewModel (gardenID : String) : ViewModel() {
    private val gardens = MutableLiveData<List<Garden>>()
}