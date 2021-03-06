package com.example.drugstore.presentation.order

import android.util.Log
import androidx.lifecycle.*
import com.example.drugstore.data.models.Order
import com.example.drugstore.service.OrderService
import com.example.drugstore.service.ProductService
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OrderVM @Inject constructor(
    private val orderService: OrderService,
    private val productService: ProductService,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _statusOrder: MutableLiveData<Boolean> = MutableLiveData(false)
    val getStatusOrder: LiveData<Boolean> get() = _statusOrder

    fun getOrderByID(ID:String) = liveData(Dispatchers.IO) {
        when(val result = orderService.fetchOrderByID(ID)){
            is Result.Success ->{
                _statusOrder.postValue(result.data?.Status ?: false)
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
        var cur = -1
        for (i in 0 until order.ProductList.size){
            val product = productService.fetchProductByID(order.ProductList[i].ProID)
            if (product != null) {
                if(productService.updateProduct(
                    order.ProductList[i].ProID.toString(),
                    hashMapOf(Constants.PRODUCT_QUANTITY to product.Quantity - order.ProductList[i].Quantity)
                ).data == false) {
                    cur = i
                    break
                }
            }else {
                cur = i
                break
            }
        }
        if(cur != -1){
            for(i in 0 until cur){
                val product = productService.fetchProductByID(order.ProductList[i].ProID)
                productService.updateProduct(
                    order.ProductList[i].ProID.toString(),
                    hashMapOf(Constants.PRODUCT_QUANTITY to product!!.Quantity + order.ProductList[i].Quantity)
                )
            }
            emit("")
        }

        when(val result = orderService.insertOrder(order)){
            is Result.Success -> {
                emit(result.data.toString())
            }
            else -> {
                emit("")
                Log.e("ViewModel--Order","Post non-success")
            }
        }
    }

    fun acceptOrder(orderID: String?) {
        viewModelScope.launch {
            val dataUpdate: HashMap<String,Any> = hashMapOf()
            dataUpdate[Constants.PRODUCT_STATUS] = true
            dataUpdate[Constants.PRODUCT_DATE_RECEIVE] = Date()
            _statusOrder.postValue(orderService.acceptOrder(orderID, dataUpdate))
        }
    }

    fun getOrdersByProduct(name: String) = liveData(Dispatchers.IO){
        when(val result = orderService.fetchOrdersByProduct(name)){
            is Result.Success -> emit(result.data)
            else -> {
                emit(result.data)
                result.message?.let { Log.e("ViewModel--Order", it) }
            }
        }
    }
}