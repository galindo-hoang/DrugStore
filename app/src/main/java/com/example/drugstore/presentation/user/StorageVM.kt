package com.example.drugstore.presentation.user

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.service.StorageService
import com.example.drugstore.utils.Result
import kotlinx.coroutines.*
import javax.inject.Inject

class StorageVM @Inject constructor(
    private val storageService: StorageService
): ViewModel() {
    fun uploadImageToStorage(uri: String) = liveData(Dispatchers.IO){
        when(val result = storageService.uploadImageToStorage(uri)){
            is Result.Success -> {
                result.data?.let { Log.e("=======", it) }
                emit(result.data)
            }
            else -> {
                result?.message?.let { Log.e("ViewModel--Storage", it) }
                emit(null)
            }
        }
    }
}