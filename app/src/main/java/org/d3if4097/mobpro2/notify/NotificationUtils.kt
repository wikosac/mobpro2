package org.d3if4097.mobpro2.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import org.d3if4097.mobpro2.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(context: Context) {
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notif_channel_id)
    )
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(context.getString(R.string.notif_title))
        .setContentText(context.getString(R.string.notif_message))
    createChannel(context)
    notify(NOTIFICATION_ID, builder.build())
}

private fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            context.getString(R.string.notif_channel_id),
            context.getString(R.string.notif_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(false)
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = context.getString(R.string.notif_channel_desc)
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(notificationChannel)
    }
}
