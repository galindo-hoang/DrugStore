package com.example.drugstore.service

import android.util.Log
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.User
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.presentation.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val context: BaseActivity,
    private val userRepo: UserRepo,
    private val authRepo: AuthRepo
) {
    suspend fun connectUserByGoogle(user: FirebaseUser) {
        delay(3000);
        Log.d("suspend", "in suspend")

        val userCheck = userRepo.fetchUserByID(user.uid)

        if (userCheck == null) {
            userRepo.registerUser(
                User(
                    UserID = user.uid,
                    UserName = user.email.toString()
                )
            )
        }
    }

    suspend fun connectUserByPhone(user: FirebaseUser) {
        val userCheck = userRepo.fetchUserByID(user.uid)

        if (userCheck == null) {
            userRepo.registerUser(
                User(
                    UserID = user.uid,
                    UserName = user.phoneNumber.toString(),
                    PhoneNumber = user.phoneNumber.toString()
                )
            )
        }
    }

    suspend fun signOut() {
        if (isGoogleSignIn()) {
            authRepo.signOutGoogle()
        }
        authRepo.signOut()
    }

    private fun isGoogleSignIn() =
        GoogleSignIn.getLastSignedInAccount(context) != null

    fun setLanguageCode(code: String) {
        authRepo.setLanguageCode(code)
    }

    fun getGoogleSignInIntent() = authRepo.getGoogleSignInIntent()

    fun getPhoneAuthOptionsBuilder(
        phoneNumber: String,
        resendToken: PhoneAuthProvider.ForceResendingToken?
    ): PhoneAuthOptions.Builder {
        val builder = PhoneAuthOptions.newBuilder(authRepo.getFirebaseAuth())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        if (resendToken != null) {
            builder.setForceResendingToken(resendToken)
        }
        return builder
    }

    fun isAuth() = authRepo.getCurrentUserId() != null
}