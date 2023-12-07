package io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FireStoreClient {

    fun SetSurveyFilled(hostId: String): Boolean {
        var success = false
        val db = Firebase.firestore
        val surveyData = hashMapOf(
            "surveyFilled" to true
        )

        db.collection("surveys").document(hostId)
            .set(surveyData)
            .addOnSuccessListener {
                success = true
                Log.d(
                    "FirebaseStorageClient",
                    "DocumentSnapshot successfully updated! with host id: $hostId"
                )
            }
            .addOnFailureListener { e ->
                success = false
                Log.w("FirebaseStorageClient", "Error updating document", e)
            }
        return success
    }

    fun UpdateFirebaseMessagingToken(token: String, hostId: String): Boolean {
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

    suspend fun isSurveyFilledForUser(userId: String): Boolean {
        val db = Firebase.firestore

        return try {
            val documentSnapshot = db.collection("surveys").document(userId).get().await()
            documentSnapshot.exists() && documentSnapshot.getBoolean("surveyFilled") == true
        } catch (e: Exception) {
            Log.w("FirebaseStorageClient", "not filled", e)
            false
        }
    }

}