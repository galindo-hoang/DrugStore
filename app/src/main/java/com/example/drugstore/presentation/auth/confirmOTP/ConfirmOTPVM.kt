package com.example.drugstore.presentation.auth.confirmOTP

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.UserService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class ConfirmOTPVM @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    fun connectUserByPhone(user: FirebaseUser) {
        userService.connectUserByPhone(user)
    }
}