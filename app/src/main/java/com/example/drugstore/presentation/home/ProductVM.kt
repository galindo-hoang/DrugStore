package com.example.drugstore.presentation.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Product
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.admin.home.AddProductActivity
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

    fun updateProduct(dataUpdate: HashMap<String,Any>,ID: String,addProductActivity: AddProductActivity) {
        viewModelScope.launch {
            if(dataUpdate.containsKey(Constants.PRODUCT_URL_IMAGE)) {
                dataUpdate[Constants.PRODUCT_URL_IMAGE] = storageService.uploadImageProductToStorage("product",
                    dataUpdate[Constants.PRODUCT_URL_IMAGE] as String,ID).toString()
            }
            when(productService.updateProduct(ID,dataUpdate)){
                is Result.Success -> addProductActivity.finish()
                else -> Toast.makeText(addProductActivity,"cant add product",Toast.LENGTH_SHORT).show()
            }
            addProductActivity.hideProgressDialog()
        }
    }

    fun addProduct(product: Product,addProductActivity: AddProductActivity) {
        viewModelScope.launch {
            val count: Int? = productService.countProduct().data
            if (product.ProImage != "") {
                product.ProImage =
                    storageService.uploadImageProductToStorage(
                        "product",
                        product.ProImage,
                        count.toString()
                    ).data.toString()
            }
            if (count == null) Toast.makeText(addProductActivity,"cant add product",Toast.LENGTH_SHORT).show()
            else {
                product.ProID = count
                when (productService.addProduct(product)) {
                    is Result.Success -> addProductActivity.finish()
                    else -> Toast.makeText(addProductActivity,"cant add product",Toast.LENGTH_SHORT).show()
                }
            }
            addProductActivity.hideProgressDialog()
        }

    }


}