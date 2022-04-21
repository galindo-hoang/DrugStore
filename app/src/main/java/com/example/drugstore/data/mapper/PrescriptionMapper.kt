package com.example.drugstore.data.mapper

import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.models.Prescription
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionMapper @Inject constructor(
    private val prescriptionDetailMapper: PrescriptionDetailMapper
) {
    fun toEntity(
        prescriptionDto: PrescriptionDto,
    ): Prescription =
        Prescription(
            startDate = prescriptionDto.startDate,
            endDate = prescriptionDto.endDate,
            time = mapOf(
                "hours" to prescriptionDto.hours,
                "minutes" to prescriptionDto.minutes
            ),
            prescriptionDetails = prescriptionDto.prescriptionDetails.map { item ->
                prescriptionDetailMapper.toEntity(item)
            }
        )
}