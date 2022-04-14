package com.example.drugstore.presentation.auth

import android.content.Intent
import android.os.Bundle
import com.example.drugstore.databinding.ActivityMainBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.auth.inputPhone.InputPhoneActivity
import com.example.drugstore.presentation.auth.signIn.SignInActivity

class MainActivity : BaseActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, InputPhoneActivity::class.java))
        }
    }

}