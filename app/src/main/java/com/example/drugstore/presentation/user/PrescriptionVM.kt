package com.example.drugstore.presentation.user

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.service.PrescriptionService
import com.example.drugstore.utils.Response
import com.example.drugstore.utils.Status
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class PrescriptionVM @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ActivityContext private val context: Context,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private val _startDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _endDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _time: MutableLiveData<Pair<Int, Int>> = MutableLiveData(Pair(0, 0))
    private val _prescriptionDetails: MutableLiveData<List<PrescriptionDetailDto>> =
        MutableLiveData()

    val startDate: LiveData<Date> get() = _startDate
    val endDate: LiveData<Date> get() = _endDate
    val time: LiveData<Pair<Int, Int>> get() = _time
    val prescriptionDetails: LiveData<List<PrescriptionDetailDto>> get() = _prescriptionDetails

    fun updateTime(time: Pair<Int, Int>) {
        _time.value = time
    }

    fun updateStartDate(date: Date) {
        if (date.before(_endDate.value!!)) {
            _startDate.value = date
        } else {
            showError("Start date must be before end date")
        }
    }

    fun updateEndDate(date: Date) {
        if (_startDate.value!!.before(date)) {
            _endDate.value = date
        } else {
            showError("End date must be after start date")
        }
    }

    fun getPrescription() {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.getPrescription().run {
                if (status == Status.SUCCESS) {
                    _startDate.postValue(data!!.startDate)
                    _endDate.postValue(data.endDate)
                    _time.postValue(Pair(data.hours, data.minutes))
                } else if (status == Status.ERROR) {
                    showError(message)
                }
            }
        }
    }

    private fun showError(message: String?) = viewModelScope.launch(
        Dispatchers.Main
    ) {
        Toast.makeText(
            context,
            "Error: ${message.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun updatePrescription() {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.updatePrescription(
                _startDate.value,
                _endDate.value,
                _time.value
            )
                .run {
                    if (status == Status.ERROR) {
                        Log.d("HAGL", "Error: ${message.toString()}")
                    }
                }
        }
    }

    fun savePrescription() {
        TODO("Not yet implemented")
    }
}
