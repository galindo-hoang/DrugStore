package com.example.drugstore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drugstore.data.models.Address

@Dao
interface AddressDao {
    @Insert
    suspend fun insertAddress(address: Address)

    @Query("select * from address")
    fun fetchAllAddress(): LiveData<List<Address>>

    @Query("select * from address where address.id =:ID")
    fun fetchAddressByID(ID: Int): LiveData<Address>
}