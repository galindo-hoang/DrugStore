package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            val document = collection.document().get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("fetchUserByID", "addOnCanceledListener")
            null
        }
    }

    fun registerUser(user: User): Boolean {
        var result = false
        collection
            .document(user.UserID)
            .set(user)
            .addOnSuccessListener {
                result = true
            }
        return result
    }

    fun retrieveUser(id: String): MutableLiveData<User> {
        val result: MutableLiveData<User> = MutableLiveData()

        collection
            .document(id)
            .get()
            .addOnSuccessListener {
                result.postValue(it.toObject(User::class.java))
            }
            .addOnCanceledListener {
                result.postValue(null)
            }

        return result
    }

    //    fun getUserByID()
}