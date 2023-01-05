package org.d3if4097.mobpro2.notify

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val notificationManager = ContextCompat.getSystemService(
            context, NotificationManager::class.java)
        notificationManager?.sendNotification(context)
    }
}