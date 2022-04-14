package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("user")

    fun fetchUserByID(Id: String): User? {
        var result: User? = null
        collection
            .document(Id)
            .get()
            .addOnSuccessListener {
                result = it.toObject(User::class.java)
            }
            .addOnCanceledListener {
                Log.e("fetchUserByID", "addOnCanceledListener");
            }
        return result
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

    fun retrieveUser(): MutableLiveData<User> {
        var result: MutableLiveData<User> = MutableLiveData()
        collection
            .document(FirebaseClass.getCurrentUserId())
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