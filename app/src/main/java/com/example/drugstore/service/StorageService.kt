package com.example.drugstore.service

import android.net.Uri
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.StorageRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageService @Inject constructor(
    private val authRepo: AuthRepo,
    private val storageRepo: StorageRepo
) {
    suspend fun uploadImageToStorage(uri: String) =
        authRepo.getCurrentUserId()?.let { storageRepo.uploadImageToStorage(uri, it) }
}