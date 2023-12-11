package io.github.viabachelora23michaelkutaibakasper.bprapp.data.authentication

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FireStoreClient {
    fun updateFirebaseMessagingToken(token: String, hostId: String): Boolean {
        var success = false
        val db = Firebase.firestore
        val tokenData = hashMapOf(
            "token" to token
        )

        db.collection("notifications").document(hostId)
            .set(tokenData)
            .addOnSuccessListener {
                success = true
                Log.d(
                    "FirebaseStorageClient",
                    "DocumentSnapshot successfully updated! with token and hostId: $token and $hostId"
                )
            }
            .addOnFailureListener { e ->
                success = false
                Log.w("FirebaseStorageClient", "Error updating document", e)
            }
        return success
    }
}