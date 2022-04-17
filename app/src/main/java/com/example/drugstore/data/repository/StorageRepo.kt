package com.example.drugstore.data.repository

import android.net.Uri
import android.util.Log
import com.example.drugstore.utils.Result
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepo @Inject constructor() {
    suspend fun uploadImageToStorage(uri: String, ID:String): Result<String> = withContext(Dispatchers.IO){
        val sRef = FirebaseStorage.getInstance().reference.child("profile/$ID.png")
        try {
            Result.Success(
                sRef
                    .putFile(Uri.parse(uri))
                    .await()
                    .storage
                    .downloadUrl
                    .await().toString())
        }catch (e: Exception){
            Result.Error("cant storage data","")
        }
    }
}