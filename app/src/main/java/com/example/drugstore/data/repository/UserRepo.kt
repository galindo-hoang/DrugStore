package com.example.drugstore.data.repository

import android.util.Log
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.User
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FieldValue
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
            Log.e("Repository--User", e.message.toString())
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

    suspend fun updateUser(ID: String, dataUser: HashMap<String, Any>): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                collection.document(ID).update(dataUser).await()
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), false)
            }
        }

    suspend fun fetchAllUser(): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            val documents = collection.get().await()
            val result = mutableListOf<User>()
            for (i in documents) result.add(i.toObject(User::class.java))
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message.toString(), null)
        }
    }

    suspend fun addAddress(address: Address, currentUserId: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                collection.document(currentUserId)
                    .update(Constants.USER_ADDRESS, FieldValue.arrayUnion(address)).await()
                true
            } catch (e: Exception) {
                Log.e("Repository--User", e.message.toString())
                false
            }
        }

    suspend fun updateAddresses(newList: MutableList<Address>, currentUserId: String): Boolean {
        return try {
            collection.document(currentUserId)
                .update(Constants.USER_ADDRESS, newList).await()
            true
        } catch (e: Exception) {
            Log.e("Repository--User", e.message.toString())
            false
        }
    }
}