package com.example.drugstore.service

import com.example.drugstore.data.models.Address
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.utils.Result
import javax.inject.Inject

class UserService @Inject constructor(
    private val authRepo: AuthRepo,
    private val userRepo: UserRepo
) {
    suspend fun getUserAddress(): Result<List<Address>?> =
        authRepo.getCurrentUserId()?.let { userId ->
            userRepo.fetchUserByID(userId)?.let { user ->
                Result.Success(user.Address)
            } ?: Result.Error("User not found")
        } ?: Result.Error("Unauthenticated");

}