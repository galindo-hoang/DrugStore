package com.example.drugstore.presentation.order

import android.util.Log
import androidx.lifecycle.*
import com.example.drugstore.data.models.Order
import com.example.drugstore.service.SOrderRepo
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderVM: ViewModel() {
    companion object{
        val result:MutableLiveData<Order?> = MutableLiveData()
    }
    fun setOrderByID(ID:String) = viewModelScope.launch {
        when(val resource = SOrderRepo().fetchOrderByID(ID)){
            is Result.Success ->{
                result.postValue(resource.data)
            }
            else -> {
                Log.e("ViewModel--Order","cant get data")
                result.postValue(null)
            }
        }
    }

    fun getOrderByID() = result

    fun getOrderByUser(ID:String,status: Boolean) = liveData(Dispatchers.IO){
        when(val result = SOrderRepo().fetchOrderByUser(ID,status)){
            is Result.Success -> emit(result.data)
            else -> {
                emit(null)
                Log.e("ViewModel--Order","get non-success")
            }
        }
    }

    fun insertOrder(order: Order) = liveData(Dispatchers.IO) {
        when(val result = SOrderRepo().insertOrder(order)){
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