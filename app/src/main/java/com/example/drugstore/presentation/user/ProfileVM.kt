package com.example.drugstore.presentation.user

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.UserService
import javax.inject.Inject

class ProfileVM @Inject constructor(
    userService: UserService
) : ViewModel() {

}