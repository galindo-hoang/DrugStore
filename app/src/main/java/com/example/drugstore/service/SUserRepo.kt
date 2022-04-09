package com.example.drugstore.service

import com.example.drugstore.data.models.User
import com.example.drugstore.data.repository.UserRepo
import com.google.firebase.auth.FirebaseUser

class SUserRepo {
    fun connectUserByGoogle(user: FirebaseUser) {
        val userCheck = UserRepo().fetchUserByID(user.uid)
        if(userCheck == null){
            UserRepo().registerUser(User(UserID = user.uid, UserName = user.email.toString()))
        }
    }

    fun connectUserByPhone(user: FirebaseUser){
        val userCheck = UserRepo().fetchUserByID(user.uid)
        if(userCheck == null){
            UserRepo().registerUser(User(UserID = user.uid, UserName = user.phoneNumber.toString(), PhoneNumber = user.phoneNumber.toString()))
        }
    }

}