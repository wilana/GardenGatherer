package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.WSM1120464.gardengatherer.databinding.ActivityPlantInfoBinding
import com.google.android.material.slider.RangeSlider


class PlantInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rangeSliderBloomTime.addOnChangeListener { slider, value, fromUser ->
            // update text views with accurate month
            binding.textViewFirstBloomMonth.text = getMonth(slider.valueFrom.toInt())
            binding.textViewLastBloomMonth.text = getMonth(slider.valueTo.toInt())

        }
    }

    /**
     * Get month in string form from an int
     */
    private fun getMonth(value: Int): String {
        var month = "Unselected"
        when (value) {
            1 -> month = "January"
            2 -> month = "February"
            3 -> month = "March"
            4 -> month = "April"
            5 -> month = "May"
            6 -> month = "June"
            7 -> month = "July"
            8 -> month = "August"
            9 -> month = "September"
            10 -> month = "October"
            11 -> month = "November"
            12 -> month = "December"
            else -> month = "Error"
        }
        return month
    }
}