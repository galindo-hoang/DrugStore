package com.example.drugstore.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Address
import com.example.drugstore.service.AddressService
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddressVM(val application: Application): ViewModel() {

    fun getListAddresses() = AddressService(application).getAllAddress()
    fun getAddressByID(ID: Int) = AddressService(application).getAddressById(ID)

    fun insertAddress(title: String,longitude:Double,latitude:Double,address: String,PhoneNumber:String) = viewModelScope.launch {
        AddressService(application).insertAddress(Address(
            longitude = longitude,
            latitude = latitude,
            title = title,
            address = address,
            phoneNumber = PhoneNumber
        ))
    }


    class AddressVMFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if( modelClass.isAssignableFrom(AddressVM::class.java)) return AddressVM(application) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }

    }
}