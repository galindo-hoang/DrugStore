package com.example.drugstore.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepo @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val firebaseAuth: FirebaseAuth
) {
    fun signOut() {
        firebaseAuth.signOut()
    }

    suspend fun signOutGoogle() {
        withContext(Dispatchers.IO) {
            googleSignInClient.signOut().await()
        }
    }

    fun signInWithCredentials(credential: AuthCredential) =
        firebaseAuth.signInWithCredential(credential)

    fun setLanguageCode(code: String) {
        firebaseAuth.setLanguageCode(code)
    }

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent

    fun getFirebaseAuth(): FirebaseAuth {
        return firebaseAuth
    }

    fun getCurrentUserId() =
        firebaseAuth.currentUser?.uid
}