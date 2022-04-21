package com.example.drugstore.presentation.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.drugstore.databinding.ActivitySplashBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.admin.MainAdminActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.presentation.user.ProfileVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var authVM: AuthVM
    @Inject lateinit var profileVM: ProfileVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()


        if (!authVM.isAuth()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            profileVM.getCurrentUser().observe(this){
                if (it != null) {
                    if(it.Permission == 0) startActivity(Intent(this, HomeActivity::class.java))
                    else startActivity(Intent(this, MainAdminActivity::class.java))
                }else startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}