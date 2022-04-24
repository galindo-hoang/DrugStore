package com.example.drugstore.presentation.order

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.drugstore.data.models.*
import com.example.drugstore.databinding.ActivityAddPlaceBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.user.ProfileVM
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Constants.isNetworkAvailable
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddPlaceActivity : BaseActivity() {
//    private var profile: Boolean = false
    private var latLong: LatLng? = null
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityAddPlaceBinding
    private lateinit var mapVM: MapVM
    @Inject lateinit var profileVM: ProfileVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mapVM = ViewModelProvider(this).get()
        mapVM.selectedItem.observe(this) {
            convertLatLongToAddress(it)
        }

        setUpCurrentRequestLocation()

        checkPermissionCurrentLocation()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            insertAddress()
        }
    }

    private fun insertAddress() {
        val title = binding.tvTitle.text.toString()
        val phoneNumber = binding.tvPhoneNumber.text.toString()
        if(title != "" && phoneNumber != ""){
            profileVM.addAddress(
                Address(
                    longitude = mapVM.selectedItem.value?.longitude ?: 0.0,
                    latitude = mapVM.selectedItem.value?.latitude ?: 0.0,
                    phoneNumber = phoneNumber,
                    address = binding.tvCurrentLocation.text.toString(),
                    title = title,
                    false),
                this)
        }else{
            Toast.makeText(this,"Please input place holder",Toast.LENGTH_SHORT).show()
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mLocationRequest.priority = android.location.LocationRequest.QUALITY_HIGH_ACCURACY
        }
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            latLong = LatLng(p0!!.lastLocation.latitude, p0.lastLocation.longitude)
            convertLatLongToAddress(latLong!!)
            if(isNetworkAvailable(this@AddPlaceActivity)) {
                transactionGoogleMapFragment()
            }
            else Toast.makeText(this@AddPlaceActivity,"Please access internet",Toast.LENGTH_LONG).show()
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