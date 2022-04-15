package com.example.drugstore.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.drugstore.service.ProductService
import com.example.drugstore.utils.Respond
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers

class ProductVM: ViewModel() {
    fun getAllListProducts() = ProductService().fetchAllProducts()
    fun getListProductsByCategory(id: Int) = ProductService().fetchProductsByCategory(id)
    fun getProductsWithSearch(search:String) = liveData(Dispatchers.IO){
        when(val result = ProductService().fetchProductsWithSearch(search)){
            is Result.Success -> emit(result.data)
            else -> {
                emit(result.data)
                Log.e("ViewModel--Product","Cant get data")
            }
        }
    }
}