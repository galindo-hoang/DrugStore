package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Notification
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("notification")

    suspend fun addNotification(notification: Notification):Result<Notification> = withContext(Dispatchers.IO){
        try {
            val result = collection.document()
            notification.id = result.id
            result.set(notification).await()
            Result.Success(notification)
        }catch (e: Exception){
            Result.Error(e.message.toString(),null)
        }
    }

    suspend fun fetchNotificationByUserID(ID: String): Result<List<Notification>> = withContext(Dispatchers.IO){
        try {
            val documents = collection.whereArrayContains(Constants.LIST_USER,ID).get().await()
            val result = mutableListOf<Notification>()
            for(i in documents) result.add(i.toObject(Notification::class.java))
            Result.Success(result)
        }catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message.toString(),null)
        }
    }
}