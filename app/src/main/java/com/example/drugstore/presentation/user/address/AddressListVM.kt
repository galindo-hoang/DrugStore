package com.example.drugstore.presentation.user.address

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Address
import com.example.drugstore.service.UserService
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KFunction0
import com.example.drugstore.utils.Result

class AddressListVM @Inject constructor(
    @ActivityContext private val context: Context,
    private val userService: UserService
) : ViewModel() {
    private val _addresses = MutableLiveData<List<Address>>();

    val addresses: LiveData<List<Address>>
        get() = _addresses

    private var _location = LatLng(0.0, 0.0)

    private val _addressText = MutableLiveData<String>()
    val addressText: LiveData<String>
        get() = _addressText

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    fun fetchAddresses() {
        viewModelScope.launch {
            userService.getUserAddress().run {
                if (this is Result.Success) {
                    _addresses.postValue(data!!)
                } else if (this is Result.Error) {
                    showError(message!!);
                }
            }
        }
    }

    suspend fun showError(msg: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Error: $msg",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun setAddress(location: LatLng) {
        viewModelScope.launch(Dispatchers.Default) {
            val addressList: List<android.location.Address>? = Geocoder(
                context,
                Locale.getDefault()
            ).getFromLocation(location.latitude, location.longitude, 1)

            if (addressList != null && addressList.isNotEmpty()) {
                val address: android.location.Address = addressList[0]
                val sb = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append(",")
                }
                sb.deleteCharAt(sb.length - 1)
                _location = location
                _addressText.postValue(sb.toString())
            }
        }
    }

    fun onConfirm(
        title: String?,
        phone: String,
        type: String,
        callback: KFunction0<Unit>
    ) {
        val address = Address(
            longitude = _location.longitude,
            latitude = _location.latitude,
            phoneNumber = phone,
            address = addressText.value!!,
            title = title!!,
            false
        )

        if (type == AddressInfoActivity.ADD) {
            val action = suspend { userService.addAddress(address) }
            onResult(action, callback)
        } else {
            val action = suspend { userService.updateAddress(address) }
            onResult(action, callback)
        }
    }

    private fun onResult(
        action: suspend () -> Result<Boolean?>,
        callback: KFunction0<Unit>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            action.invoke().run {
                if (this is Result.Success) {
                    callback.invoke()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun getAddress(title: String, moveCamera: (Double, Double) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            userService.getAddress(title).run {
                if (this is Result.Success) {
                    _address.postValue(data!!)
                    withContext(Dispatchers.Main) {
                        moveCamera(data.latitude, data.longitude)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun removeAddress(address: Address) {
        val listAddresses = addresses.value!!.toMutableList()
        listAddresses.remove(address)

        viewModelScope.launch(Dispatchers.IO) {
            userService.removeAddress(listAddresses).run {
                if (this is Result.Success) {
                    _addresses.postValue(listAddresses)
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}