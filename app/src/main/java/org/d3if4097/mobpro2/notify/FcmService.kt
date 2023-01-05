package org.d3if4097.mobpro2.notify

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("FCM", "Token baru: $token")
    }
}