package com.example.drugstore.presentation.user

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Address
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.auth.SplashActivity
import com.example.drugstore.service.AuthService
import com.example.drugstore.service.StorageService
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileVM @Inject constructor(
    private val authService: AuthService,
    private val storageService: StorageService
) : ViewModel() {
    private val _listAddress:MutableLiveData<List<Address>> = MutableLiveData()
    val getListAddress: MutableLiveData<List<Address>> get() = _listAddress
    fun signOut(context: BaseActivity) {
        viewModelScope.launch {
            authService.signOut()
            withContext(Dispatchers.Main) {
                context.startActivity(Intent(context, SplashActivity::class.java))
            }
            context.finishAffinity()
        }
    }

    fun getCurrentUser() = liveData(Dispatchers.IO){
        emit(authService.findUserByID())
    }

    fun setupListAddress() {
        viewModelScope.launch {
            _listAddress.postValue(
                authService.findUserByID()?.Address ?: listOf()
            )
        }
    }

    fun addAddress(address: Address, addPlaceActivity: Activity) {
        viewModelScope.launch {
            val callback = Intent()
            if(authService.addAddress(address) == true) {
                callback.putExtra(Constants.ADDRESS,true)
            }
            else {
                Toast.makeText(addPlaceActivity,"cant add this address",Toast.LENGTH_SHORT).show()
                callback.putExtra(Constants.ADDRESS,false)
            }
            addPlaceActivity.setResult(Activity.RESULT_OK,callback)
            addPlaceActivity.finish()
        }
    }


    fun updateUser(dataUser: HashMap<String, Any>, updateProfileActivity: BaseActivity) {
        viewModelScope.launch {
            if(dataUser.containsKey(Constants.USER_URL_IMAGE)){
                dataUser[Constants.USER_URL_IMAGE] = storageService.uploadImageToStorage("profile",dataUser[Constants.USER_URL_IMAGE].toString())?.data.toString()
            }
            when(authService.updateUser(dataUser)){
                is Result.Success -> updateProfileActivity.finish()
                else -> {
                    Toast.makeText(updateProfileActivity,"cant update profile",Toast.LENGTH_SHORT).show()
                }
            }
            updateProfileActivity.hideProgressDialog()
        }
    }

}