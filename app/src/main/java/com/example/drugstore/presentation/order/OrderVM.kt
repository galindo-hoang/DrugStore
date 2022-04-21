package com.example.drugstore.presentation.order

import android.util.Log
import androidx.lifecycle.*
import com.example.drugstore.data.models.Order
import com.example.drugstore.service.OrderService
import com.example.drugstore.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderVM @Inject constructor(
    private val orderService: OrderService,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    fun setOrderByID(ID:String) = liveData(Dispatchers.IO) {
        when(val result = orderService.fetchOrderByID(ID)){
            is Result.Success ->{
                emit(result.data)
            }
            else -> {
                result.message?.let { Log.e("ViewModel--Order", it) }
                emit(result.data)
            }
        }
    }

    fun getAllOrder() = liveData(Dispatchers.IO){
        when(val result = orderService.fetchAllOrder()){
            is Result.Success -> emit(result.data)
            else -> {
                emit(null)
                Log.e("ViewModel--Order","get non-success")
            }
        }
    }


    fun getOrderByUser(status: Boolean) = liveData(Dispatchers.IO){
        when(val result =
            firebaseAuth.currentUser?.let { orderService.fetchOrderByUser(it.uid,status) }){
            is Result.Success -> emit(result.data)
            else -> {
                emit(null)
                Log.e("ViewModel--Order","get non-success")
            }
        }
    }

    fun insertOrder(order: Order) = liveData(Dispatchers.IO) {
        when(val result = orderService.insertOrder(order)){
            is Result.Success -> {
                Log.e("ViewModel--Order","Post success")
                emit(result.data.toString())
            }
            else -> {
                emit("")
                Log.e("ViewModel--Order","Post non-success")
            }
        }
    }
}