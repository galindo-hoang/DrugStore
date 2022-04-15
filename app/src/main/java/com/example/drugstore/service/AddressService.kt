package com.example.drugstore.service

import android.app.Application
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.repository.AddressRepo

class AddressService(val application: Application) {

    fun getAllAddress() = AddressRepo(application).fetchAllAddress()

    fun getAddressById(ID:Int) = AddressRepo(application).fetchAddressByID(ID)

    suspend fun insertAddress(address: Address) = AddressRepo(application).insertAddress(address)
}