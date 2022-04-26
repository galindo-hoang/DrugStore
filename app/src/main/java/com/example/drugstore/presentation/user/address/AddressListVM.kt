package com.example.drugstore.presentation.user.address

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Address
import com.example.drugstore.service.UserService
import com.example.drugstore.utils.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressListVM @Inject constructor(
    @ActivityContext private val context: Context,
    private val userService: UserService
) : ViewModel() {
    private val _addresses = MutableLiveData<List<Address>>();

    val addresses: LiveData<List<Address>>
        get() = _addresses

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
}