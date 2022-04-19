package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.User
import com.example.drugstore.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("user")

    suspend fun fetchUserByID(Id: String): User? = withContext(Dispatchers.IO) {
        try {
            val document = collection.document(Id).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("fetchUserByID", "addOnCanceledListener")
            null
        }
    }

    fun connectUser(user: User): Boolean {
        var result = false
        collection
            .document(user.UserID)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                result = true
            }
        return result
    }

    suspend fun updateUser(ID:String, dataUser: HashMap<String,Any>): Result<String> = withContext(Dispatchers.IO){
        try {
            collection.document(ID).update(dataUser).await()
            Result.Success("Update Profile Success")
        }catch (e:Exception){
            Result.Error("cant update profile","cant update profile")
        }
    }
}