package com.example.drugstore.presentation.auth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.service.AuthService
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthVM @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    fun postPhoneSignIn(user: FirebaseUser, context: BaseActivity) {
        viewModelScope.launch {
            val isNewUser = authService.connectUserByPhone(user)
            postConnect(context, isNewUser)
        }
    }

    fun postGoogleSignIn(user: FirebaseUser, context: BaseActivity) {
        viewModelScope.launch {
            val isNewUser = authService.connectUserByGoogle(user)
            Log.d("suspend", "out suspend")
            postConnect(context, isNewUser)
        }
    }

    private suspend fun postConnect(context: BaseActivity, isNewUser: Boolean) {
        withContext(Dispatchers.Main) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra("isNewUser", isNewUser)
            context.startActivity(
                intent
            )
            context.finish()
        }
    }

    fun getGoogleSignInIntent() = authService.getGoogleSignInIntent()

    fun setLanguageCode(code: String) {
        authService.setLanguageCode(code)
    }

    fun authenticateFirebase(
        credential: AuthCredential,
        context: BaseActivity,
        onSuccess: (user: FirebaseUser, context: BaseActivity) -> Unit
    ) {
        FirebaseClass.getFirebaseAuth().signInWithCredential(credential)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    val user = task.result.user!!
                    Log.d("---", user.phoneNumber.toString())
                    onSuccess(user, context)
                } else {
                    Log.w("---", "signInWithCredential:failure", task.exception)
                }
            }
    }

    suspend fun signOut() {
        authService.signOut()
    }

    fun verifyPhoneNumber(
        phoneNumber: String,
        context: BaseActivity,
        resendToken: PhoneAuthProvider.ForceResendingToken?,
        onCodeSentOverride: (
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) -> Unit
    ) {
        authService.setLanguageCode("vi")

        val options = authService.getPhoneAuthOptionsBuilder(phoneNumber, resendToken)
            .setActivity(context)                 // Activity (for callback binding)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        authenticateFirebase(
                            credential,
                            context,
                            ::postPhoneSignIn
                        )
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w("---", "onVerificationFailed", e)

                        if (e is FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.e("---", "Invalid request")
                        } else if (e is FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            Log.e("---", "The SMS quota for the project has been exceeded")
                        }

                        // Show a message and update the UI
                        Toast.makeText(
                            context,
                            "Verification Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) = onCodeSentOverride(verificationId, token)
                }
            )          // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun isAuth(): Boolean = authService.isAuth()
}