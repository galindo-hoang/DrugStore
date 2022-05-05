package com.example.drugstore.presentation.user.reminder

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
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import com.example.drugstore.utils.Result

class NewPrescriptionVM @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ActivityContext private val context: Context,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private val _name: MutableLiveData<String> = MutableLiveData()
    private val _startDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _endDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _time: MutableLiveData<Pair<Int, Int>> = MutableLiveData(Pair(0, 0))
    private val _prescriptionDetails: MutableLiveData<MutableList<PrescriptionDetailDto>> =
        MutableLiveData()

    val name: LiveData<String> get() = _name
    val startDate: LiveData<Date> get() = _startDate
    val endDate: LiveData<Date> get() = _endDate
    val time: LiveData<Pair<Int, Int>> get() = _time
    val prescriptionDetails: LiveData<MutableList<PrescriptionDetailDto>>
        get() = _prescriptionDetails

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
            prescriptionService.getPrescriptionDto().run {
                if (this is Result.Success) {
                    _startDate.postValue(data!!.startDate)
                    _endDate.postValue(data.endDate)
                    _time.postValue(Pair(data.hours, data.minutes))
                    _prescriptionDetails.postValue(data.prescriptionDetails.toMutableList())
                } else if (this is Result.Error) {
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

    fun updatePrescription(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.updatePrescription(
                newName,
                _startDate.value,
                _endDate.value,
                _time.value
            )
                .run {
                    if (this is Result.Error) {
                        Log.d("HAGL", "Error: ${message.toString()}")
                    }
                }
        }
    }

    fun increaseQuantity(prescriptionDetailDto: PrescriptionDetailDto) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.increaseQuantity(
                prescriptionDetailDto
            )
                .run {
                    if (this is Result.Success) {
                        val data = _prescriptionDetails.value!!
                        prescriptionDetailDto.quantity += 1
                        _prescriptionDetails.postValue(data)
                    } else if (this is Result.Error) {
                        Log.d("HAGL", "Error: ${message.toString()}")
                    }
                }
        }
    }

    fun decreaseQuantity(prescriptionDetailDto: PrescriptionDetailDto) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.decreaseQuantity(
                prescriptionDetailDto
            )
                .run {
                    if (this is Result.Success) {
                        val data = _prescriptionDetails.value!!
                        prescriptionDetailDto.quantity -= 1
                        _prescriptionDetails.postValue(data)
                    } else if (this is Result.Error) {
                        Log.d("HAGL", "Error: ${message.toString()}")
                    }
                }
        }
    }

    fun deletePrescription(prescriptionDetailDto: PrescriptionDetailDto) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.deletePrescriptionDetailDto(
                prescriptionDetailDto
            )
                .run {
                    if (this is Result.Success) {
                        val data = _prescriptionDetails.value!!
                        data.remove(prescriptionDetailDto)
                        _prescriptionDetails.postValue(data)
                    } else if (this is Result.Error) {
                        Log.d("HAGL", "Error: ${message.toString()}")
                    }
                }
        }
    }

    fun savePrescription(onSaveSuccess: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.savePrescription()
                .run {
                    if (this is Result.Success) {
                        onSaveSuccess(data!!)
                        prescriptionService.clearLocal()
                    } else if (this is Result.Error) {
                        showError(message)
                    }
                }
        }
    }
}
