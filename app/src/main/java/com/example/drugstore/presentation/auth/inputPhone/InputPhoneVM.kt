package com.example.drugstore.presentation.auth.inputPhone

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.UserService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class InputPhoneVM @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    fun connectUserByPhone(user: FirebaseUser) {
        userService.connectUserByPhone(user)
    }
}