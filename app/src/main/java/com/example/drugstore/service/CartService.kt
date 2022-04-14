package com.example.drugstore.service

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.CartRepo

class CartService (private val application: Application) {
    
    fun fetchAllProducts(): LiveData<List<CartProduct>> {
        return CartRepo(application).fetchAllProducts()
    }

    fun fetchProductById(id: Int): LiveData<CartProduct> {
        return CartRepo(application).fetchProductById(id = id)
    }

    fun getQuantityProducts(): LiveData<Int> {
        return CartRepo(application).getQuantityProducts()
    }

    suspend fun insertProduct(product: Product){
        CartRepo(application).insertProduct(
            CartProduct(
                ProID = product.ProID,
                ProName = product.ProName,
                Quantity = 1,
                Price = product.Price,
                ProImage = product.ProImage
            )
        )
    }

    suspend fun updateQuantityProduct(quantity: Int,proId: Int, increase: Boolean){
        if (increase) CartRepo(application).updateQuantityProduct(quantity + 1,proId)
        else{
            if(quantity > 1) CartRepo(application).updateQuantityProduct(quantity - 1,proId)
            else CartRepo(application).deleteProduct(proId)
        }
    }

}