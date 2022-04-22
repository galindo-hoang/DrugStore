package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Chat
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepo @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    suspend fun sendMess(path:String, orderID:String, chat: Chat) = withContext(Dispatchers.IO){
        try {
            firebaseDatabase.getReference(path).child(orderID).push().setValue(chat)
            true
        }catch (e:Exception){
            false
        }
    }

    fun setupReceiveMess(path:String, orderID:String, valueEventListener: ValueEventListener) = firebaseDatabase.getReference(path).child(orderID).addValueEventListener(valueEventListener)

    fun removeReceiveMess(path:String, orderID:String,valueEventListener: ValueEventListener) = firebaseDatabase.getReference(path).child(orderID).removeEventListener(valueEventListener)
}