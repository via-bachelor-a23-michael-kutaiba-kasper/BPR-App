package io.github.viabachelora23michaelkutaibakasper.bprapp.notifications

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.FireStoreClient

class NotificationClient : FirebaseMessagingService() {
    var user = mutableStateOf(Firebase.auth.currentUser)


    fun getToken(): String {
        var token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Log.d(TAG, "token is: $token")
        })
        return token
    }

    override fun onNewToken(token: String) {
        Log.d("Firebase notifications", "Refreshed token: $token")
        if (user.value == null)
            return
        FireStoreClient().updateFirebaseMessagingToken(token, user.value!!.uid)

    }
}