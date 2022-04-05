package com.example.drugstore.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.drugstore.data.models.CartProduct

@Dao
interface CartProductDao {
    @Query("select * from cart ")
    fun fetchAllProducts(): LiveData<List<CartProduct>>

    @Query("select count(cart.ProID) from cart order by cart.ProID")
    fun getQuantityProducts(): LiveData<Int>

    @Insert
    suspend fun insertProduct(product: CartProduct)

    @Query("select * from cart where cart.ProID=:id")
    fun fetchProductById(id: Int):LiveData<CartProduct>

    @Query("update cart set Quantity=:quantity where cart.ProID=:proId")
    suspend fun updateQuantityProduct(quantity: Int,proId: Int)

    @Query("delete from cart where cart.ProID=:id")
    suspend fun deleteProduct(id: Int)
}