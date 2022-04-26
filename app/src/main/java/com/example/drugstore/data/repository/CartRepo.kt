package com.example.drugstore.data.repository

import androidx.lifecycle.LiveData
import com.example.drugstore.data.local.dao.CartProductDao
import com.example.drugstore.data.models.CartProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepo @Inject constructor(
    private var cartProductDao: CartProductDao
) {

    fun fetchAllProducts(): LiveData<List<CartProduct>>{
        return cartProductDao.fetchAllProducts()
    }

    fun fetchProductById(id: Int): LiveData<CartProduct>{
        return cartProductDao.fetchProductById(id = id)
    }

    fun getQuantityProducts(): LiveData<Int>{
        return cartProductDao.getQuantityProducts()
    }

    suspend fun insertProduct(product: CartProduct) = cartProductDao.insertProduct(product)

    suspend fun updateQuantityProduct(quantity: Int,proId: Int) = cartProductDao.updateQuantityProduct(quantity,proId)

    suspend fun deleteProduct(id: Int) = cartProductDao.deleteProduct(id)

    suspend fun deleteAll() = cartProductDao.deleteAll()
}