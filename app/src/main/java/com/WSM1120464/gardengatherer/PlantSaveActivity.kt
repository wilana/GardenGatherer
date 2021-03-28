package com.WSM1120464.gardengatherer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.WSM1120464.gardengatherer.databinding.ActivityPlantSaveBinding
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider


class PlantSaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // update labels when range slider is touched
        binding.rangeSliderBloomTime.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }
        })

        // update Labels with month as string
        binding.rangeSliderBloomTime.setLabelFormatter( LabelFormatter {value ->
            getMonth(value.toInt())
        })
    }

    /**
     * Update textViews for both months
     */
    private fun updateMonths(values: MutableList<Float>) {
        binding.textViewFirstBloomMonth.text = getMonth(values[0].toInt())
        binding.textViewLastBloomMonth.text = getMonth(values[1].toInt())
    }

    /**
     * Get month in string form from an int
     */
    private fun getMonth(value: Int): String {
        return when (value) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Error"
        }
    }
}