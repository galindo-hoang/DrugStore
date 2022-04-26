package com.example.drugstore.service

import com.example.drugstore.data.models.Order
import com.example.drugstore.data.repository.OrderRepo
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderService @Inject constructor(
    private val orderRepo: OrderRepo
) {
    suspend fun insertOrder(order: Order) = orderRepo.insertOrder(order)
    suspend fun fetchOrderByID(orderID:String) = orderRepo.fetchOrderByID(orderID)
    suspend fun fetchOrderByUser(ID: String,status:Boolean) = orderRepo.fetchOrderByUser(ID,status)
    suspend fun fetchAllOrder() = orderRepo.fetchAllOrder()
    suspend fun acceptOrder(orderID: String?, dataUpdate: HashMap<String, Any>) = orderRepo.acceptOrder(orderID,dataUpdate)
}