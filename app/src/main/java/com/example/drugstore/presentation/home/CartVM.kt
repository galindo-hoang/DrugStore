package com.example.drugstore.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.CartService
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartVM @Inject constructor(
    private val cartService: CartService
): ViewModel() {

    fun getCartProducts(): LiveData<List<CartProduct>> = cartService.fetchAllProducts()

    fun getProductById(id: Int): LiveData<CartProduct> = cartService.fetchProductById(id)


    fun getQuantityProducts(): LiveData<Int> = cartService.getQuantityProducts()

    fun insertProduct(product: Product) = viewModelScope.launch {
        cartService.insertProduct(product)
    }

    fun increaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        cartService.updateQuantityProduct(quantity,ProID,true)
    }

    fun decreaseQuantityProduct(quantity: Int, ProID: Int) = viewModelScope.launch {
        cartService.updateQuantityProduct(quantity,ProID,false)
    }

    fun deleteAll() = viewModelScope.launch {
        cartService.deleteAll()
    }
}