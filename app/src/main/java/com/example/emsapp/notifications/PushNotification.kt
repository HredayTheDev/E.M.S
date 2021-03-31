package com.example.emsapp.notifications

import com.example.emsapp.notifications.NotificationData

data class PushNotification(
        val data: NotificationData,
        val to: String
)
