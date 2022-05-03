package com.example.drugstore.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.databinding.ActivityConfirmOtpBinding
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.utils.Constants
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmOTPActivity : BaseActivity() {
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var binding: ActivityConfirmOtpBinding
    private lateinit var verification: String
    private lateinit var phoneNumber: String

    @Inject
    lateinit var authVM: AuthVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        setUpToolBar()

        if (intent.hasExtra(Constants.PHONE_NUMBER)) {
            phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER)!!
            binding.etPhoneNumber.text = phoneNumber
        }
        if (intent.hasExtra(Constants.VERIFICATION_ID)) verification =
            intent.getStringExtra(Constants.VERIFICATION_ID)!!

        binding.btnNext.setOnClickListener {
            setUpNext()
        }

        binding.tvRequestNew.setOnClickListener {
            authVM.verifyPhoneNumber(phoneNumber, this, resendToken, ::onCodeSent)
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.tb)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
        }
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpNext() {
        val otp = binding.etOTP.text.toString().trim()
        if (otp.length == 6) {
            val credential = PhoneAuthProvider.getCredential(verification, otp)
            authVM.authenticateFirebase(
                credential,
                this@ConfirmOTPActivity,
                authVM::postPhoneSignIn
            )
        } else {
            Toast.makeText(this, "OTP invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
        Log.d("---", "onCodeSent:$verificationId")
        // Save verification ID and resending token so we can use them later
        verification = verificationId
        resendToken = token
    }
}