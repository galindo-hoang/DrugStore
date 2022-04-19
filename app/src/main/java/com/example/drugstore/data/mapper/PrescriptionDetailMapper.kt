package com.example.drugstore.data.mapper

import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.models.PrescriptionDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionDetailMapper @Inject constructor() {
    fun toEntity(prescriptionDetailDto: PrescriptionDetailDto) =
        PrescriptionDetail(
            productId = prescriptionDetailDto.productId,
            quantity = prescriptionDetailDto.quantity,
        )
}