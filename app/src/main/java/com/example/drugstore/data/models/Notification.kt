package com.example.drugstore.data.models

import android.app.NotificationManager
import com.example.drugstore.R
import java.util.*

data class Notification(
    var id:String = "",
    var title:String = "",
    var body:String = "",
    var largeIcon:String = "",
    var priority:Int = NotificationManager.IMPORTANCE_DEFAULT,
    // 0: add product
    // 1: chat
    // 2: reminder
    var type:Int = 0,
    var contentType:String = "",
    var listToken: List<String> = listOf(),
    var listUser: List<String> = listOf(),
    var channelID:Int = R.string.default_notification_channel_id,
    var time:Date = Date(),
//    var clicked:Boolean = false
)
