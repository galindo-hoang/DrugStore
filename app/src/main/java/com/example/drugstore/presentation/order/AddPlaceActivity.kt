package com.example.drugstore.presentation.order

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.drugstore.databinding.ActivityAddPlaceBinding
import com.example.drugstore.utils.Constants
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.util.*

class AddPlaceActivity : AppCompatActivity() {
    private var latLong: LatLng? = null
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: MapVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get()
        viewModel.selectedItem.observe(this) {
            convertLatLongToAddress(it)
        }

        setUpCurrentRequestLocation()

        checkPermissionCurrentLocation()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            Log.e("----", viewModel.selectedItem.value.toString())
        }
    }

    private fun checkPermissionCurrentLocation() {
        if(isEnableLocation()){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),Constants.REQUEST_CURRENT_LOCATION)
            }else{
                fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!)
            }
        }else{
            Toast.makeText(this,"Please turn on Location access",Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEnableLocation():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_CURRENT_LOCATION -> {
                if(!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUpCurrentRequestLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = android.location.LocationRequest.QUALITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            latLong = LatLng(p0!!.lastLocation.latitude, p0.lastLocation.longitude)
            convertLatLongToAddress(latLong!!)
            if(isNetworkAvailable()) {
                transactionGoogleMapFragment()
            }
            else Toast.makeText(this@AddPlaceActivity,"Please access internet",Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable():Boolean{

        val connectivityManager = this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)
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

    private fun convertLatLongToAddress(latLng: LatLng) {
        val addressList:List<Address>? = Geocoder(this@AddPlaceActivity, Locale.getDefault()).getFromLocation(latLng.latitude,latLng.longitude,1)

        if (addressList != null && addressList.isNotEmpty()) {
            val address: Address = addressList[0]
            val sb = StringBuilder()
            for (i in 0..address.maxAddressLineIndex) {
                sb.append(address.getAddressLine(i)).append(",")
            }
            sb.deleteCharAt(sb.length - 1)
            binding.tvCurrentLocation.text = sb
        }
    }

    private fun transactionGoogleMapFragment() {
        val fmTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        if(latLong != null){
            bundle.putDouble(Constants.LONGITUDE, latLong!!.longitude)
            bundle.putDouble(Constants.LATITUDE, latLong!!.latitude)
        }
        val addPlacesFragment = AddPlacesFragment()
        addPlacesFragment.arguments = bundle
        fmTransaction.replace(binding.flmap.id,addPlacesFragment)
        fmTransaction.commit()
    }
}