package com.example.drugstore.service

import androidx.lifecycle.LiveData
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.CartRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartService @Inject constructor(
    private val cartRepo: CartRepo
) {
    
    fun fetchAllProducts(): LiveData<List<CartProduct>> {
        return cartRepo.fetchAllProducts()
    }

    fun fetchProductById(id: Int): LiveData<CartProduct> {
        return cartRepo.fetchProductById(id = id)
    }

    fun getQuantityProducts(): LiveData<Int> {
        return cartRepo.getQuantityProducts()
    }

    suspend fun insertProduct(product: Product){
        cartRepo.insertProduct(
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
        if (increase) cartRepo.updateQuantityProduct(quantity + 1,proId)
        else{
            if(quantity > 1) cartRepo.updateQuantityProduct(quantity - 1,proId)
            else cartRepo.deleteProduct(proId)
        }
    }

    suspend fun deleteAll() = cartRepo.deleteAll()

}