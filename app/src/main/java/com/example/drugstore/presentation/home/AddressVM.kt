package com.example.drugstore.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Address
import com.example.drugstore.service.SAddressRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddressVM(val application: Application): ViewModel() {

    fun getListAddresses() = SAddressRepo(application).getAllAddress()
    fun getAddressByID(ID: Int) = SAddressRepo(application).getAddressById(ID)

    fun insertAddress(title: String,longitude:Double,latitude:Double,address: String,PhoneNumber:String) = viewModelScope.launch {
        SAddressRepo(application).insertAddress(Address(
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