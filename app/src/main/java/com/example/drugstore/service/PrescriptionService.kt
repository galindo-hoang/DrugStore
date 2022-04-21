package com.example.drugstore.service

import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.mapper.PrescriptionMapper
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.data.repository.PrescriptionRepo
import com.example.drugstore.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionService @Inject constructor(
    private val prescriptionRepo: PrescriptionRepo,
    private val prescriptionDao: PrescriptionDao,
    private val prescriptionMapper: PrescriptionMapper
) {
    suspend fun getPrescription(): Response<PrescriptionDto> {
        val value = prescriptionDao.getPrescription()

        return if (value.isEmpty()) {
            Response.error(null, "No prescription found")
        } else {
            Response.success(value.first())
        }
    }

    suspend fun checkExists() = prescriptionDao.getPrescription().isEmpty()

    suspend fun updatePrescription(
        startDate: Date?,
        endDate: Date?,
        time: Pair<Int, Int>?
    ): Response<Long> {
        val rowId = prescriptionDao.insertProduct(
            PrescriptionDto().also { prescription ->
                startDate?.let { prescription.startDate = it }
                endDate?.let { prescription.endDate = it }
                time?.let {
                    prescription.hours = it.first
                    prescription.minutes = it.second
                }
            }
        )

        return if (rowId > 0) {
            Response.success(rowId)
        } else {
            Response.error(null, "Failed to update prescription")
        }
    }


}