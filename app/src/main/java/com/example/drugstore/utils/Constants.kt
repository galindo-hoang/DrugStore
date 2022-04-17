package com.example.drugstore.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.drugstore.data.models.User

object Constants {

    const val BIRTH_DATE: String = "birthday"
    const val USER_NAME:String = "userName"
    const val ADDRESS: String = "address"
    const val URL_IMAGE: String = "userImage"
    const val GENDER:String = "gender"


    const val PRODUCT_NAME: String = "proName"
    const val DATE_ORDER: String = "dateOrder"
    const val STATUS: String = "status"
    const val PRODUCT_ID: String = "proID"
    const val ORDER_ID: String = "ORDER_ID"
    const val USER_ID: String = "userID"
    const val OBJECT_ARTICLE: String = "OBJECT_ARTICLE"
    const val TOPIC_NAME: String = "TOPIC_NAME"
    const val OBJECT_PRODUCT: String = "OBJECT_PRODUCT"
    const val CAT_ID: String = "catID"
    const val REQUEST_CURRENT_LOCATION: Int = 1
    const val REQUEST_STORAGE: Int = 2
    const val LATITUDE: String = "LATITUDE"
    const val LONGITUDE: String = "LONGITUDE"
    const val OBJECT_CATEGORY: String = "OBJECT_CATEGORY"
    const val VERIFICATION_ID: String = "VERIFICATION_ID"
    const val PHONE_NUMBER: String = "phoneNumber"
    const val BASE_URL_NEWS:String = "https://newsapi.org/"

    fun isNetworkAvailable(context: Context):Boolean{

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
        else {
            return connectivityManager.activeNetworkInfo != null &&
                    connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
        }
    }

    fun checkPermissionRead(context: Context):Boolean{
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}