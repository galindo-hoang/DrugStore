package com.example.drugstore.presentation.home

//class AddressVM(val application: Application): ViewModel() {
//
//    fun getListAddresses() = AddressService(application).getAllAddress()
//    fun getAddressByID(ID: Int) = AddressService(application).getAddressById(ID)
//
//    fun insertAddress(title: String,longitude:Double,latitude:Double,address: String,PhoneNumber:String) = viewModelScope.launch {
//        AddressService(application).insertAddress(Address(
//            longitude = longitude,
//            latitude = latitude,
//            title = title,
//            address = address,
//            phoneNumber = PhoneNumber
//        ))
//    }
//
//
//    class AddressVMFactory(private val application: Application): ViewModelProvider.Factory{
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if( modelClass.isAssignableFrom(AddressVM::class.java)) return AddressVM(application) as T
//            throw IllegalArgumentException("Unable construct viewModel")
//        }
//
//    }
//}