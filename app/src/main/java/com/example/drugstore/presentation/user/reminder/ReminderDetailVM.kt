package com.example.drugstore.presentation.user.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.data.models.PrescriptionDetail
import com.example.drugstore.service.PrescriptionService
import javax.inject.Inject
import com.example.drugstore.utils.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ReminderDetailVM @Inject constructor(
    @ActivityContext private val context: Context,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private val _startDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _endDate: MutableLiveData<Date> = MutableLiveData(Date())
    private val _time: MutableLiveData<Pair<Int, Int>> = MutableLiveData(Pair(0, 0))
    private val _prescriptionDetails: MutableLiveData<MutableList<PrescriptionDetail>> =
        MutableLiveData()

    val startDate: LiveData<Date> get() = _startDate
    val endDate: LiveData<Date> get() = _endDate
    val time: LiveData<Pair<Int, Int>> get() = _time
    val prescriptionDetails: LiveData<MutableList<PrescriptionDetail>>
        get() = _prescriptionDetails

    fun getPrescription(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.getPrescription(id, includeAll = true)
                .run {
                    if (this is Result.Success) {
                        _startDate.postValue(data!!.startDate!!)
                        _endDate.postValue(data.endDate!!)
                        _time.postValue(
                            Pair(
                                data.time!!["hours"]!!.toInt(),
                                data.time["minutes"]!!.toInt()
                            )
                        )
                        data.prescriptionDetails?.let {
                            _prescriptionDetails.postValue(it.toMutableList())
                        }
                    } else if (this is Result.Error) {
                        showError(message)
                    }
                }
        }
    }

    private suspend fun showError(message: String?) {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Error: ${message.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}