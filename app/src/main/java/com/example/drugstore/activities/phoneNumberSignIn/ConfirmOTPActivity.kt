package com.example.drugstore.activities.phoneNumberSignIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.drugstore.activities.BaseActivity
import com.example.drugstore.activities.HomeActivity
import com.example.drugstore.databinding.ActivityConfirmOtpBinding
import com.example.drugstore.firebase.FirebaseClass
import com.example.drugstore.utils.Constants
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ConfirmOTPActivity : BaseActivity() {
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var binding: ActivityConfirmOtpBinding
    private lateinit var verification: String
    private lateinit var phoneNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        setUpToolBar()

        if(intent.hasExtra(Constants.PHONE_NUMBER)){
            phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER)!!
            binding.tvPhoneNumber.text = phoneNumber
        }
        if(intent.hasExtra(Constants.VERIFICATION_ID)) verification = intent.getStringExtra(Constants.VERIFICATION_ID)!!

        binding.btnNext.setOnClickListener {
            setUpNext()
        }

        binding.tvRequestNew.setOnClickListener {
            setUpPhoneSignIn()
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.tb)
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
        }
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpNext() {
        val otp = binding.etOTP.text.toString().trim()
        if(otp.length == 6){
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verification,otp))
        }else{
            Toast.makeText(this,"OTP invalid",Toast.LENGTH_SHORT).show()
        }
    }


    private fun setUpPhoneSignIn(){
        FirebaseClass.getFirebaseAuth().setLanguageCode("vi")
        val options = PhoneAuthOptions.newBuilder(FirebaseClass.getFirebaseAuth())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setForceResendingToken(resendToken)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        Log.d("---", "onVerificationCompleted:${credential.provider}")
                        Log.d("---", "onVerificationCompleted:${credential.smsCode}")
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w("---", "onVerificationFailed", e)

                        if (e is FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.e("---","Invalid request")
                        } else if (e is FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            Log.e("---","The SMS quota for the project has been exceeded")
                        }

                        // Show a message and update the UI
                        Toast.makeText(this@ConfirmOTPActivity,"Verification Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d("---", "onCodeSent:$verificationId")

                        // Save verification ID and resending token so we can use them later
                        verification = verificationId
                        resendToken = token
                    }
                }
            )          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseClass.getFirebaseAuth().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("---", "signInWithCredential:success")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("---", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.e("---","The verification code entered was invalid")
                    }
                    // Update UI
                }
            }
    }
}