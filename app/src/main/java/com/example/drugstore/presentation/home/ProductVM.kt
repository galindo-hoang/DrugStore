package com.example.drugstore.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.ProductService
import com.example.drugstore.service.StorageService
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductVM @Inject constructor(
    private val productService: ProductService,
    private val storageService: StorageService
) : ViewModel() {
    fun getAllListProducts() = liveData(Dispatchers.IO) {
        when(val result = productService.fetchAllProducts()){
            is Result.Success -> emit(result.data)
            else -> {
                result.message?.let { Log.e("ViewModel--Product", it) }
                emit(result.data)
            }
        }
    }
    fun getListProductsByCategory(id: Int) = liveData(Dispatchers.IO){
        when(val result = productService.fetchProductsByCategory(id)){
            is Result.Success -> emit(result.data)
            else -> {
                result.message?.let { Log.e("ViewModel--Product", it) }
                emit(result.data)
            }
        }
    }
    fun getProductsWithSearch(search:String) = liveData(Dispatchers.IO){
        when(val result = productService.fetchProductsWithSearch(search)){
            is Result.Success -> emit(result.data)
            else -> {
                Log.e("ViewModel--Product","Cant get data")
                emit(result.data)
            }
        }
    }

    fun getNumberProducts() = liveData(Dispatchers.IO) {
        when(val result = productService.countProduct()){
            is Result.Success -> emit(result.data)
            else -> {
                result.message?.let { Log.e("ViewModel--Product", it) }
                emit(result.data)
            }
        }
    }

    fun addProduct(product: Product) = liveData(Dispatchers.IO){
        var count:Int? = null
        val a = viewModelScope.launch {
            when(val countResult = productService.countProduct()){
                is Result.Success -> count = countResult.data
            }
        }
        a.join()
        if(count == null) emit(false)
        else{
            product.ProID = count as Int
            if(product.ProImage != "") storageService.uploadImageProductToStorage("product",product.ProImage,count.toString())
            when(val result = productService.addProduct(product)){
                is Result.Success -> emit(result.data)
                else -> {
                    Log.e("ViewModel--Product",result.message.toString())
                    emit(result.data)
                }
            }
        }
    }

    fun updateProduct(dataUpdate: HashMap<String,Any>,ID: String) = liveData(Dispatchers.IO){
        if(dataUpdate.containsKey(Constants.PRODUCT_URL_IMAGE)) storageService.uploadImageProductToStorage("product",
            dataUpdate[Constants.PRODUCT_URL_IMAGE] as String,ID)
        when(val result = productService.updateProduct(ID,dataUpdate)){
            is Result.Success -> emit(result.data)
            else -> {
                Log.e("ViewModel--Product",result.message.toString())
                emit(result.data)
            }
        }
    }


}