package com.example.drugstore.service

import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.mapper.PrescriptionMapper
import com.example.drugstore.data.repository.PrescriptionRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionService @Inject constructor(
    private val prescriptionRepo: PrescriptionRepo,
    private val prescriptionDao: PrescriptionDao,
    private val prescriptionMapper: PrescriptionMapper
) {
}