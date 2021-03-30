package com.example.emsapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emsapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_equipment.*
import java.util.*


class AddEquipmentActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private val equipmentList = ArrayList<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_equipment)


        firebaseDatabase = Firebase.database

        val equipmentRef = firebaseDatabase.reference.child("equipment")

        val equipmentList = resources.getStringArray(R.array.equipmentList)
        val equipmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, equipmentList)
        equipmentName_auto_complete_tv.setAdapter(equipmentAdapter)



        saveEquipmentBtn.setOnClickListener{
            val equipmentName = equipmentName_auto_complete_tv.text
            val equipmentItemCount = equipment_item_count_text_input_et.text

            Toast.makeText(this, "Click $equipmentName $equipmentItemCount", Toast.LENGTH_SHORT).show()
        }


    }
}