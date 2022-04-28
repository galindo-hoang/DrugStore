package com.example.drugstore.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.StrictMode
import android.util.Log
import androidx.core.app.ActivityCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*


object Constants {

    const val FCM_TOKEN_UPDATE: String = "fcmTokenUpdate"
    const val FCM_TOKEN: String = "fcmToken"
    const val  DRUGSTORE_PREFERENCES = "DRUGSTORE_PREFERENCES"


    const val PRODUCT_DATE_RECEIVE: String = "dateReceive"
    const val PRODUCT_STATUS: String = "status"
    const val PRODUCT_URL_IMAGE: String = "proImage"
    const val DESCRIPTION: String = "description"
    const val PRODUCT_QUANTITY: String = "quantity"
    const val PRODUCT_PRICE: String = "price"
    const val PRODUCT_NAME: String = "proName"


    const val BIRTH_DATE: String = "birthday"
    const val USER_NAME:String = "userName"
    const val ADDRESS: String = "address"
    const val USER_URL_IMAGE: String = "userImage"
    const val GENDER:String = "gender"
    const val USER_ADDRESS: String = "address"


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

    fun getNotificationId(): Int {
        return Date().time.toInt()
    }



    const val REMOTE_MSG_AUTHORIZATION = "Authorization"
    const val REMOTE_MSG_CONTENT_TYPE = "Content-Type"
    const val REMOTE_MSG_DATA = "data"
    const val REMOTE_MSG_REGISTRATION_IDS = "registration_ids"

    var remoteMsgHeaders:HashMap<String,String>? = hashMapOf(
        REMOTE_MSG_AUTHORIZATION to "key=AAAAqYRpWb0:APA91bEfISCx8Sv35Xff-TK0XXctShJ-y5DNY2MPF3GkCHWSPSPuojLwuD87cTiBZOFu7Ulkjjzip8fAcxGwsDS0CUjIoLAceffK2MiTp45VBLoejBSGX-nC0b1VVl5LfM1-qku4357z",
        REMOTE_MSG_CONTENT_TYPE to "application/json")

    const val BASE_URL_MESS = "https://fcm.googleapis.com/fcm/send"
    const val SERVER_KEY = "key=AAAAqYRpWb0:APA91bEfISCx8Sv35Xff-TK0XXctShJ-y5DNY2MPF3GkCHWSPSPuojLwuD87cTiBZOFu7Ulkjjzip8fAcxGwsDS0CUjIoLAceffK2MiTp45VBLoejBSGX-nC0b1VVl5LfM1-qku4357z"

    fun pushNotification(context: Context,token: String){
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val queue = Volley.newRequestQueue(context)

        try {
            val json = JSONObject()
            json.put("to",token)
            val notification = JSONObject()
            notification.put("title","cc")
            notification.put("body","lz")
            json.put("notification",notification)

            val req: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                BASE_URL_MESS,
                json,
                Response.Listener { response -> Log.e("FCM", response.toString()) }, Response.ErrorListener {
                    error ->
                    error.message?.let { Log.e("====", it) }
                }) {
                /**
                 * Passing some request headers
                 */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json";
                    headers["Authorization"] = SERVER_KEY
                    return headers
                }
            }

            queue.add(req)


        }catch (e:JSONException){
            e.printStackTrace()
        }
    }

//    CoroutineScope(Dispatchers.IO).launch {
//
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.e("========token", "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//            val token = task.result
//            binding.etSearch.setText(token)
//            Log.e("========token", token)
//        })
//    }

}