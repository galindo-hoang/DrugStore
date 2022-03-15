package com.example.drugstore.activities

import android.content.Intent
import android.os.Bundle
import com.example.drugstore.activities.phoneNumberSignIn.InputPhoneActivity
import com.example.drugstore.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,InputPhoneActivity::class.java))
        }
    }

}