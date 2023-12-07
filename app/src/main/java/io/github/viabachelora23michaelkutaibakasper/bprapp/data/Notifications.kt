package io.github.viabachelora23michaelkutaibakasper.bprapp.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.FireStoreClient

class Notifications() : FirebaseMessagingService() {
    var user = mutableStateOf(Firebase.auth.currentUser)

    override fun onNewToken(token: String) {
        Log.d("Firebase notifications", "Refreshed token: $token")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        FireStoreClient().UpdateFirebaseMessagingToken(token, user.value!!.uid)
    }

}