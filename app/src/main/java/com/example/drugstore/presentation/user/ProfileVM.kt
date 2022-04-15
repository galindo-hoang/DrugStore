package com.example.drugstore.presentation.user

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.auth.SplashActivity
import com.example.drugstore.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileVM @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    fun signOut(context: BaseActivity) {
        viewModelScope.launch {
            authService.signOut()
            withContext(Dispatchers.Main) {
                context.startActivity(Intent(context, SplashActivity::class.java))
            }
        }
    }
}