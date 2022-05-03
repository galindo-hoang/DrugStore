package com.example.drugstore.presentation.notify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.drugstore.service.NotificationService
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NotificationVM @Inject constructor(
    private val notificationService: NotificationService
):ViewModel() {
    fun getNotification() = liveData(Dispatchers.IO){
        when(val result = notificationService.fetchNotificationByUserID()){
            is Result.Success -> emit(result.data)
            else -> {
                result?.message?.let { Log.e("ViewModel--Notification", it) }
                emit(listOf())
            }
        }
    }
}