package com.example.drugstore.presentation.user.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.data.models.PrescriptionDetail
import com.example.drugstore.service.PrescriptionService
import com.example.drugstore.utils.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ReminderVM @Inject constructor(
    @ActivityContext private val context: Context,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private val _prescriptions = MutableLiveData<List<Prescription>>(mutableListOf())

    val prescriptions: LiveData<List<Prescription>>
        get() = _prescriptions

    fun fetchPrescription() {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.getAllPrescriptions().run {
                if (this is Result.Success) {
                    _prescriptions.postValue(data!!)
                } else {
                    showError(message.toString())
                }
            }
        }
    }

    private fun showError(error: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, "Error: ${error}", Toast.LENGTH_LONG).show()
        }
    }

    fun delete(prescription: Prescription) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.deletePrescription(prescription.id!!).run {
                if (this is Result.Success) {
                    var value = _prescriptions.value!!.toMutableList()

                    _prescriptions.postValue(
                        value.filter { existPrescription -> existPrescription.id != prescription.id })
                } else {
                    showError(message.toString())
                }
            }
        }
    }
}