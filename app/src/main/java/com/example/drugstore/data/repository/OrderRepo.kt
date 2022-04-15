package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Order
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class OrderRepo {
    private val collection = FirebaseFirestore.getInstance().collection("order")

    suspend fun insertOrder(order: Order): Result<String> = withContext(Dispatchers.IO) {
        val result = collection.document()
        result.set(order).await()
        result.update(hashMapOf("orderID" to result.id) as Map<String, String>).await()
        Result.Success(result.id)
    }

    suspend fun fetchOrderByID(ID:String): Result<Order?> = withContext(Dispatchers.IO){
        val model: Order?
        val document = collection.document(ID).get().await()
        model = document.toObject(Order::class.java)
        if(model == null){
            Result.Error("cant fetch data",model)
        }else Result.Success(model)
    }

    suspend fun fetchOrderByUser(userID:String, status: Boolean): Result<List<Order>> = withContext(Dispatchers.IO){
        val document = collection
            .whereEqualTo(Constants.USER_ID,userID)
            .whereEqualTo(Constants.STATUS,status)
            .get().await()
        val result: MutableList<Order> = mutableListOf()
        for(i in document){
            result.add(i.toObject(Order::class.java))
        }
        if(result.size != 0) Result.Success(result)
        else Result.Error("cant fetch data",result)
    }
}