package com.example.drugstore.data.firebase

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.drugstore.R
import com.example.drugstore.presentation.chat.ChatActivity
import com.example.drugstore.presentation.home.ProductDetailActivity
import com.example.drugstore.presentation.user.reminder.PrescriptionActivity
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Constants.getNotificationId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class Message: FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let { sendNotification(it) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(mess: RemoteMessage.Notification){
        var intent:Intent
        when(mess.tag.toString().toInt()){
            0 -> {
                intent = Intent(this,ProductDetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_ID, mess.clickAction?.toInt() ?: -1)
            }
            1 -> {
                intent = Intent(this,ChatActivity::class.java)
                intent.putExtra(Constants.ORDER_ID,mess.clickAction)
            }
            else -> {
                intent = Intent(this,PrescriptionActivity::class.java)
            }
        }

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, getNotificationId(),intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val channelId = this.resources.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notificationCompat = NotificationCompat.Builder(this, channelId)
            .setContentTitle(mess.title)
            .setContentText(mess.body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(mess.icon?.let { getBitmapFromURL(it) })
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(mess.notificationPriority!!)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(getNotificationId(),notificationCompat)
    }
    private fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            System.out.printf("src", src)
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            var myBitmap: Bitmap = BitmapFactory.decodeStream(input)
            myBitmap = Bitmap.createScaledBitmap(
                myBitmap,
                100,
                100,
                false
            )//This is only if u want to set the image size.
            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            System.out.printf("Exception", e.printStackTrace())
            null
        }
    }

}