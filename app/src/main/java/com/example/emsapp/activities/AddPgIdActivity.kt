package com.example.emsapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emsapp.R
import com.example.emsapp.models.PgNumber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_pg_id.*
import kotlinx.android.synthetic.main.activity_main.*

class AddPgIdActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var pgId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pg_id)

        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        val pgIdRef = firebaseDatabase.getReference("pgNumber")

        addPgIdBtn.setOnClickListener {
            if (!validatePgId()) {
                return@setOnClickListener
            }
            progressbar.visibility = View.VISIBLE

            val pushKey = pgIdRef.push().key

            if (pushKey == null) {
                Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_LONG).show()
                progressbar.visibility = View.GONE
                return@setOnClickListener
            }

            val pgNumber = PgNumber(pushKey, pgId)
            pgIdRef.child(pushKey).setValue(pgNumber).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Added Successful", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                }
            }

            progressbar.visibility = View.GONE

        }


    }

    private fun validatePgId(): Boolean {
        pgId = pgIdET.editText?.text.toString().trim()
        return if (pgId.isEmpty()) {
            pgIdET.error = "Field can'nt be empty"
            false
        } else {
            pgIdET.error = null
            true
        }
    }
}