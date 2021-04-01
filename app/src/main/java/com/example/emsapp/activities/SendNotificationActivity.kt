package com.example.emsapp.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emsapp.R
import com.example.emsapp.adapters.NotificationAdapter
import com.example.emsapp.models.Employee
import com.example.emsapp.notifications.NotificationData
import com.example.emsapp.notifications.PushNotification
import com.example.emsapp.notifications.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_send_notification.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic2"

class SendNotificationActivity : AppCompatActivity(), NotificationAdapter.OnItemClickListener {

    companion object {
        private const val TAG = "SendNotification"
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notification)

        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        val notificationRef = database.child("notification")

        recyclerView.layoutManager = LinearLayoutManager(this)

        //crating an arraylist to store users using the data class user
        val employees = ArrayList<Employee>()
        //creating our adapter
        val adapter = NotificationAdapter(employees, this)

        notificationRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    employees.clear()
                    for (data in snapshot.children) {
                        val user = data.getValue(Employee::class.java)
                        employees.add(user!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })


        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onItemClick(token: String) {

        Log.d(TAG, "onItemClick: $token")

        val title = "Hasan"
        val message = "etMessage.text.toString()"
        //val recipientToken = token
        if (title.isNotEmpty() && message.isNotEmpty() && token.isNotEmpty()) {
            PushNotification(NotificationData(title, message), token).also {
                sendNotification(it)
            }
        }


    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                //Log.d(TAG, "Response: ${Gson().toJson(response)}")
                Toast.makeText(this@SendNotificationActivity, "Send notification", Toast.LENGTH_SHORT).show();
            } else {
               // Log.e(TAG, response.errorBody().toString())
                Toast.makeText(this@SendNotificationActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}