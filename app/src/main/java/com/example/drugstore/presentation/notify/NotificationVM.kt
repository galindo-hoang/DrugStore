package com.example.drugstore.presentation.notify

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Notification
import com.example.drugstore.service.NotificationService
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun insertNotification(context:Context, notification: Notification) {
        viewModelScope.launch {
            when(val result = notificationService.addNotification(notification)){
                is Result.Success -> result.data?.let {
                    Constants.pushNotification(context, it)
                }
                else -> {
                    Toast.makeText(context,"Cant push notification", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}