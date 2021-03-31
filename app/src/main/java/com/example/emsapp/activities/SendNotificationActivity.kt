package com.example.emsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emsapp.R

const val TOPIC = "/topics/myTopic2"
class SendNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notification)
    }
}