package com.example.drugstore.service

import com.example.drugstore.data.models.Order
import com.example.drugstore.data.repository.OrderRepo

class SOrderRepo {
    suspend fun insertOrder(order: Order) = OrderRepo().insertOrder(order)
    suspend fun fetchOrderByID(ID:String) = OrderRepo().fetchOrderByID(ID)
    suspend fun fetchOrderByUser(ID: String,status:Boolean) = OrderRepo().fetchOrderByUser(ID,status)
}