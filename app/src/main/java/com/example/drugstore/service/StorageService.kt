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
    suspend fun uploadImageToStorage(path:String, uri: String) =
        authRepo.getCurrentUserId()?.let { storageRepo.uploadImageToStorage(path,uri, it) }

    suspend fun uploadImageProductToStorage(path:String, uri: String,id: String) = storageRepo.uploadImageToStorage(path,uri, id)
}