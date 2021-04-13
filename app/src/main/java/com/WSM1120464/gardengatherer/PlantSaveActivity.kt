package com.WSM1120464.gardengatherer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import com.WSM1120464.gardengatherer.databinding.ActivityPlantSaveBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Add a plant to the garden
 */
class PlantSaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSaveBinding
    private val authDb = FirebaseAuth.getInstance()

    // Picture handling
    private val REQUEST_CODE = 1000
    private lateinit var filePhoto : File
    private val FILE_NAME = "photo"
    private val IMAGE_CHOOSE = 2000
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up toolbar
        setSupportActionBar(binding.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get garden to add to
        val gardenID = intent.getStringExtra("gardenID")
        val gardenName = intent.getStringExtra("gardenName")

        // update labels when range slider is touched
        binding.rangeSliderBloomTime.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                updateMonths(slider.values)
            }
        })

        // update Labels for range slider with month as string
        binding.rangeSliderBloomTime.setLabelFormatter { value: Float ->
            getMonth(value.toInt())
        }

        binding.extendedFabSavePlant.setOnClickListener {
            // Plant validation only requires name
            if (binding.editTextPlantName.text.isNotEmpty()) {
                val plant = Plant()
                plant.plantName = binding.editTextPlantName.text.toString()
                plant.plantType = binding.spinnerType.selectedItem.toString()
                plant.plantLight = binding.spinnerPlantLight.selectedItem.toString()
                plant.plantHeight = binding.editTextPlantHeight.text.toString()
                plant.plantFertilizer = binding.editTextFertilizer.text.toString()
                plant.plantPruning = binding.editTextPruning.text.toString()
                plant.plantNotes = binding.editTextPlantNotes.text.toString()
                plant.gardenID = gardenID

                val values = binding.rangeSliderBloomTime.values
                plant.plantBloomStart = values[0].toInt()
                plant.plantBloomEnd = values[1].toInt()

                // connect to db
                val db = FirebaseFirestore.getInstance().collection("plants")
                plant.plantID = db.document().id

                // add garden to db
                db.document(plant.plantID!!).set(plant)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Plant Added", Toast.LENGTH_LONG).show()
                        // change to activity
                        val intent = Intent (this, PlantsActivity::class.java)
                        intent.putExtra("gardenID", gardenID)
                        intent.putExtra("gardenName", gardenName)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }

            }
            else
                Toast.makeText(this, "Please add the plant name", Toast.LENGTH_LONG).show()
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return true
    }

    // back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logoff -> {
                authDb.signOut()
                finish()
                startActivity(Intent(this, SignInActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // Photo things from https://medium.com/developer-student-clubs/android-kotlin-camera-using-gallery-ff8591c26c3e

    //This method will return the file object for the picture (the actual .jpg)
    private fun getPhotoFile(fileName: String) : File{
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> if(resultCode == Activity.RESULT_OK) {
                val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
                binding.imageViewPlant.setImageBitmap(takenPhoto)
                val builder = Uri.Builder()
                imageUri = builder.appendPath(filePhoto.absolutePath).build()
            }
            IMAGE_CHOOSE -> {
                binding.imageViewPlant.setImageURI(data?.data)
                if (data != null) {
                    imageUri = data.data
                }
            }
            else ->  super.onActivityResult(requestCode, resultCode, data)
        }
    }



//    private fun chooseImageGallery() {
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    chooseImageGallery()
//                }else{
//                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
}