package com.example.drugstore.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.service.PrescriptionService
import java.util.*
import javax.inject.Inject

class PrescriptionVM @Inject constructor(
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
        _startDate.value = date
    }

    fun updateEndDate(date: Date) {
        _endDate.value = date
    }
}
