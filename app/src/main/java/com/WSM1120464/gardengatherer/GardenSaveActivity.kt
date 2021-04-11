package com.WSM1120464.gardengatherer


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.WSM1120464.gardengatherer.databinding.ActivityGardenSaveBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 *
 */
class GardenSaveActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGardenSaveBinding
    private val authDb = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGardenSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolBar.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var userID = intent.getStringExtra("userID")




        // try to add garden to list, then return to all gardens
        binding.extendedFabSaveGarden.setOnClickListener {
            // only validating garden name
            if (binding.editTextTextGardenName.text.toString().isNotEmpty())
            {
                // save inputted info
                val garden = Garden()
                garden.gardenName = binding.editTextTextGardenName.text.toString()
                garden.userID = userID
                garden.gardenSize = binding.editTextGardenSize.text.toString()
                garden.lightConditions = binding.spinnerGardenLight.selectedItem.toString()
                garden.gardenNotes = binding.editTextGardenNotes.text.toString()

                // connect to db
                val db = FirebaseFirestore.getInstance().collection("gardens")
                garden.gardenID = db.document().id

                // add garden to db
                db.document(garden.gardenID!!).set(garden)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Garden Added", Toast.LENGTH_LONG).show()
                        // change to activity
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userID", userID)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }

            }
            else
                Toast.makeText(this, "Please add your name and the garden name", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return true
    }

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
}