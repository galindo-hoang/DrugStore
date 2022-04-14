package com.example.drugstore.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.drugstore.R
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.databinding.ActivityInputPhoneBinding
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
class InputPhoneActivity : BaseActivity() {
    private lateinit var binding: ActivityInputPhoneBinding
    private var phoneNumber: String = ""

    @Inject
    lateinit var authVM: AuthVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        setUpToolBar()

        binding.btnNext.setOnClickListener {
            phoneNumber = binding.etPhoneNumber.text.toString().trimStart { it == '0' }
            if (phoneNumber.length in 9..11) {
                authVM.verifyPhoneNumber("+84$phoneNumber", this, null, ::onCodeSent)
            } else {
                Toast.makeText(this, "Phone number is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.tb)
        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.get_started)
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
        Log.d("---", "onCodeSent:$verificationId")
        val intent = Intent(this, ConfirmOTPActivity::class.java)
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber)
        intent.putExtra(Constants.VERIFICATION_ID, verificationId)
        startActivity(intent)
        // Save verification ID and resending token so we can use them later
        // storedVerificationId = verificationId
        // resendToken = token
    }
}