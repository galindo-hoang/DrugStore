package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Order
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("order")

    suspend fun insertOrder(order: Order): Result<String> = withContext(Dispatchers.IO) {
        try {
            val result = collection.document()
            result.set(order).await()
            result.update(hashMapOf("orderID" to result.id) as Map<String, String>).await()
            Result.Success(result.id)
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }

    suspend fun fetchOrderByID(orderID:String): Result<Order?> = withContext(Dispatchers.IO){
        try {
            val document = collection.document(orderID).get().await()
            Result.Success(document.toObject(Order::class.java))
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }

    suspend fun fetchOrderByUser(userID:String, status: Boolean): Result<List<Order>> = withContext(Dispatchers.IO){
        try {
            val document = collection
                .whereEqualTo(Constants.USER_ID, userID)
                .whereEqualTo(Constants.STATUS, status)
                .orderBy(Constants.DATE_ORDER, Query.Direction.DESCENDING)
                .get()
                .await()
            val result: MutableList<Order> = mutableListOf()
            for (i in document) result.add(i.toObject(Order::class.java))

            Result.Success(result)
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }

    suspend fun fetchAllOrder(): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val document = collection
                .orderBy(Constants.DATE_ORDER, Query.Direction.DESCENDING)
                .get()
                .await()
            val result: MutableList<Order> = mutableListOf()
            for (i in document) result.add(i.toObject(Order::class.java))
            Result.Success(result)
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }
}