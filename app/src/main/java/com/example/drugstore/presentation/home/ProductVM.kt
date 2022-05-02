package com.example.drugstore.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Product
import com.example.drugstore.presentation.admin.home.AddProductActivity
import com.example.drugstore.service.AuthService
import com.example.drugstore.service.ProductService
import com.example.drugstore.service.StorageService
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductVM @Inject constructor(
    private val productService: ProductService,
    private val storageService: StorageService,
    private val authService: AuthService
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: MutableLiveData<Product> get() = _product

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
                    is Result.Success -> {
                        val listToken = authService.getTokenFromUser()
                        for(i in listToken) Constants.pushNotification(addProductActivity,i,"add product",product.ProName,product.ProID)
                        addProductActivity.finish()
                    }
                    else -> Toast.makeText(addProductActivity,"cant add product",Toast.LENGTH_SHORT).show()
                }

            }
            addProductActivity.hideProgressDialog()
        }

    }

    fun getProductByID(ID: Int){
        viewModelScope.launch {
            _product.postValue(productService.fetchProductByID(ID))
        }
    }

}