package org.d3if4097.mobpro2.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import org.d3if4097.mobpro2.MainActivity
import org.d3if4097.mobpro2.R

private const val NOTIFICATION_ID = 0

@RequiresApi(Build.VERSION_CODES.M)
fun NotificationManager.sendNotification(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context,
        NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notif_channel_id)
    )
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(context.getString(R.string.notif_title))
        .setContentText(context.getString(R.string.notif_message))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

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
