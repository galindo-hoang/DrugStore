package com.example.drugstore.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.drugstore.data.local.CartProductDao
import com.example.drugstore.data.local.CartProductDatabase
import com.example.drugstore.data.models.CartProduct

class CartRepo(application: Application) {
    private val cartProductDao: CartProductDao
    private val cartProductDatabase: CartProductDatabase = CartProductDatabase.getInstance(application)

    init {
        cartProductDao = cartProductDatabase.getCartProductDao()
    }

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