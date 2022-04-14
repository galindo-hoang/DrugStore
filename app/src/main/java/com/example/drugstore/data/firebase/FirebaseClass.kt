package com.example.drugstore.data.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.*
import javax.inject.Inject
import javax.inject.Singleton


//user: 'webclc2@gmail.com',
//pass: 'webktpm2'
@Singleton
class FirebaseClass @Inject constructor(
    private var googleSignInClient: GoogleSignInClient
) {

    fun setGoogleSignIn(context: Context, clientID: String) {
        googleSignInClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build()
        )
    }

    fun getGoogleSignInClient(): GoogleSignInClient {
        return googleSignInClient
    }

    companion object {

        fun getFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        fun getCurrentUserId(): String {
            if (getFirebaseAuth().currentUser == null) return ""
            return getFirebaseAuth().currentUser!!.uid

        }
    }
}