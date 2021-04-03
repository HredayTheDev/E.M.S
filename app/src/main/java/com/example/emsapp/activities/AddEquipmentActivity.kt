package com.example.emsapp.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emsapp.R
import com.example.emsapp.models.Equipment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_equipment.*
import java.util.*


class AddEquipmentActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private val equipmentList = ArrayList<String?>()
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_equipment)


        firebaseDatabase = Firebase.database
        firebaseAuth = FirebaseAuth.getInstance();
        val firebaseUser = firebaseAuth.currentUser


        val equipmentRef = firebaseDatabase.reference.child("equipment")

        val equipmentList = resources.getStringArray(R.array.equipmentList)
        val equipmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, equipmentList)
        equipmentName_auto_complete_tv.setAdapter(equipmentAdapter)



        saveEquipmentBtn.setOnClickListener {

            progressbar.visibility = View.VISIBLE

            val equipmentName = equipmentName_auto_complete_tv.text.toString()
            val equipmentItemCount = equipment_item_count_text_input_et.text.toString()

            if (firebaseUser != null) {
                val currentUserId = firebaseUser.uid

                val equipment = Equipment(equipmentName, currentUserId, equipmentItemCount)
                val pushKey = equipmentRef.push().key.toString()

                equipmentRef.child(currentUserId).child(pushKey).setValue(equipment)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                                progressbar.visibility = View.GONE
                            } else {
                                Toast.makeText(this, "Not saved!", Toast.LENGTH_SHORT).show();
                                progressbar.visibility = View.GONE
                            }
                        }


                //Toast.makeText(this, "Click $equipmentName $equipmentItemCount", Toast.LENGTH_SHORT).show()
            }


        }


    }
}