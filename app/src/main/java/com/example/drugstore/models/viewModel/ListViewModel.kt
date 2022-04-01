package com.example.drugstore.models.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class ListViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<LatLng>()
    val selectedItem: LiveData<LatLng> get() = mutableSelectedItem
    fun selectItem(latLng: LatLng){
        mutableSelectedItem.value = latLng
    }
}