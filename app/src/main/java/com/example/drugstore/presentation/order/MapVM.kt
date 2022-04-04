package com.example.drugstore.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapVM: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<LatLng>()
    val selectedItem: LiveData<LatLng> get() = mutableSelectedItem
    fun selectItem(latLng: LatLng){
        mutableSelectedItem.value = latLng
    }
}