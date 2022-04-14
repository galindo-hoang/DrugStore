package com.example.drugstore.data.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.*


//user: 'webclc2@gmail.com',
//pass: 'webktpm2'
class FirebaseClass private constructor(private var googleSignInClient: GoogleSignInClient?){

    fun setGoogleSignIn(context: Context, clientID: String){
        googleSignInClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build()
        )
    }

    fun getGoogleSignInClient():GoogleSignInClient?{
        return googleSignInClient
    }

    companion object{
        private var instance:FirebaseClass? = null
        fun getInstance():FirebaseClass{
            if(instance == null){
                instance = FirebaseClass(null)
            }
            return instance!!
        }
        fun getFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        fun getCurrentUserId(): String{
            if(getFirebaseAuth().currentUser == null) return ""
            return getFirebaseAuth().currentUser!!.uid

        }
    }
}