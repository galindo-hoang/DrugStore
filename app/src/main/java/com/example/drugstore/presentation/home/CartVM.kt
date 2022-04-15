package com.example.drugstore.presentation.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.CartService
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CartVM(private val application: Application): ViewModel() {

    fun getCartProducts(): LiveData<List<CartProduct>> = CartService(application).fetchAllProducts()

    fun getProductById(id: Int): LiveData<CartProduct> = CartService(application).fetchProductById(id)


    fun getQuantityProducts(): LiveData<Int> = CartService(application).getQuantityProducts()

    fun insertProduct(product: Product) = viewModelScope.launch {
        CartService(application).insertProduct(product)
    }

    fun increaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        CartService(application).updateQuantityProduct(quantity,ProID,true)
    }

    fun decreaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        CartService(application).updateQuantityProduct(quantity,ProID,false)
    }

    fun deleteAll() = viewModelScope.launch {
        CartService(application).deleteAll()
    }

    class CartProductVMFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if( modelClass.isAssignableFrom(CartVM::class.java)) return CartVM(application) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}