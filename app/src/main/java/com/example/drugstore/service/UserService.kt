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


    suspend fun addAddress(address: Address): Result<Boolean?> {
        val persist: Boolean = getUserAddress().data?.let {
            val foundAddress = it.find { existAddress -> existAddress.title == address.title }
            foundAddress != null
        } ?: true

        return if (persist) {
            Result.Error("Address already exists")
        } else {
            authRepo.getCurrentUserId()?.let {
                if (userRepo.addAddress(address, it)) {
                    Result.Success(true)
                } else {
                    Result.Error("Address not added")
                }
            } ?: Result.Error("Unauthenticated")
        }
    }

    suspend fun updateUser(dataUser: HashMap<String, Any>) =
        authRepo.getCurrentUserId()?.let { userRepo.updateUser(it, dataUser) }

    suspend fun updateAddress(address: Address): Result<Boolean?> {
        return getUserAddress().data?.let {
            val foundAddressIndex =
                it.indexOfFirst { existAddress -> existAddress.title == address.title }

            if (foundAddressIndex == -1) {
                return Result.Error("Address not found")
            } else {
                val newList = it.toMutableList()
                newList[foundAddressIndex] = address

                authRepo.getCurrentUserId()?.let { userId ->
                    if (userRepo.updateAddresses(newList, userId)) {
                        Result.Success(true)
                    } else {
                        Result.Error("Unexpected Error")
                    }
                } ?: Result.Error("Unauthenticated")
            }
        } ?: Result.Error("Address not updated")
    }

    suspend fun getAddress(title: String): Result<Address?> {
        return getUserAddress().data?.let {
            val foundAddressIndex =
                it.indexOfFirst { existAddress -> existAddress.title == title }

            if (foundAddressIndex == -1) {
                Result.Error("Address not found")
            } else {
                Result.Success(it[foundAddressIndex])
            }
        } ?: Result.Error("Address not found")
    }
}