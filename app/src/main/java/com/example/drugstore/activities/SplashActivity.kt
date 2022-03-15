package com.example.drugstore.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivitySplashBinding
import com.example.drugstore.firebase.FirebaseClass
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
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,HomeActivity::class.java))
            }
            finish()
        },2, TimeUnit.SECONDS)
    }
}