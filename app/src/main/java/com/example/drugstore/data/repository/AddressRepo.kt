package com.example.drugstore.data.repository

import android.app.Application
import com.example.drugstore.data.local.dao.AddressDao
import com.example.drugstore.data.local.database.AddressDatabase
import com.example.drugstore.data.models.Address

class AddressRepo(application: Application) {
    private val addressDao: AddressDao
    private val addressDatabase: AddressDatabase = AddressDatabase.getInstance(application)

    init {
        addressDao = addressDatabase.getAddressDao()
    }

    fun fetchAllAddress() = addressDao.fetchAllAddress()

    fun fetchAddressByID(ID:Int) = addressDao.fetchAddressByID(ID)

    suspend fun insertAddress(address: Address) = addressDao.insertAddress(address)

}