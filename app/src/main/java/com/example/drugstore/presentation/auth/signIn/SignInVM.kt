package com.example.drugstore.presentation.auth.signIn

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.UserService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInVM @Inject constructor(
    private val userService: UserService
) : ViewModel() {

    fun signIn(user: FirebaseUser) {
        userService.connectUserByGoogle(user)
    }

}