package com.example.drugstore.presentation.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.SCartRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CartVM(private val application: Application): ViewModel() {

    fun fetchCartProducts(): LiveData<List<CartProduct>> = SCartRepo(application).fetchAllProducts()

    fun fetchProductById(id: Int): LiveData<CartProduct> = SCartRepo(application).fetchProductById(id)


    fun getQuantityProducts(): LiveData<Int> = SCartRepo(application).getQuantityProducts()

    fun insertProduct(product: Product) = viewModelScope.launch {
        SCartRepo(application).insertProduct(product)
    }

    fun increaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        SCartRepo(application).updateQuantityProduct(quantity,ProID,true)
    }

    fun decreaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        SCartRepo(application).updateQuantityProduct(quantity,ProID,false)
    }

    class CartProductVMFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if( modelClass.isAssignableFrom(CartVM::class.java)) return CartVM(application) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }

    }
}