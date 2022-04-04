package com.example.drugstore.presentation.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.drugstore.presentation.order.ChatActivity
import com.example.drugstore.databinding.ActivitySplashBinding
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.presentation.BaseActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        Executors.newSingleThreadScheduledExecutor().schedule({
            if(FirebaseClass.getFirebaseAuth().currentUser == null){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, ChatActivity::class.java))
            }
            finish()
        },2, TimeUnit.SECONDS)
    }
}