package com.example.drugstore.service

import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.local.dao.PrescriptionDetailDao
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.mapper.PrescriptionMapper
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.PrescriptionRepo
import com.example.drugstore.data.repository.ProductRepo
import com.example.drugstore.utils.Result
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionService @Inject constructor(
    private val productRepo: ProductRepo,
    private val prescriptionRepo: PrescriptionRepo,
    private val prescriptionDao: PrescriptionDao,
    private val prescriptionDetailDao: PrescriptionDetailDao,
    private val prescriptionMapper: PrescriptionMapper
) {
    suspend fun getPrescriptionDto(): Result<PrescriptionDto> {
        val prescription = prescriptionDao.getPrescription()

        return if (prescription.isEmpty()) {
            Result.Error("No prescription found")
        } else {
            val prescriptionDetail = prescriptionDetailDao.fetchAllPrescriptionDetails()
                .map { prescriptionDetailDto ->
                    prescriptionDetailDto.apply {
                        product = productRepo.fetchProduct(productId)
                    }
                    prescriptionDetailDto
                }


            val result = prescription.first().apply {
                prescriptionDetails = prescriptionDetail
            }

            Result.Success(result)
        }
    }

    suspend fun checkExists() = prescriptionDao.getPrescription().isEmpty()

    suspend fun updatePrescription(
        startDate: Date?,
        endDate: Date?,
        time: Pair<Int, Int>?
    ): Result<Long> {
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
            Result.Success(rowId)
        } else {
            Result.Error("Failed to update prescription")
        }
    }

    suspend fun addProduct(product: Product): Result<Int> {
        var result: Result<Int> = Result.Success(1)

        val prescriptionDetailDto = prescriptionDetailDao.fetchPrescriptionDetail(
            productId = product.ProID,
        )

        val numRows = if (prescriptionDetailDto.isNotEmpty()) {
            prescriptionDetailDao.updateQuantityProduct(
                productId = product.ProID,
                quantity = prescriptionDetailDto.first().quantity + 1
            )
        } else {
            prescriptionDetailDao.insertProduct(
                PrescriptionDetailDto(
                    productId = product.ProID,
                    quantity = 1
                )
            )
        }

        if (numRows == 0) {
            result = Result.Error("Failed to add product")
        }

        return result
    }

    suspend fun increaseQuantity(
        prescriptionDetailDto: PrescriptionDetailDto
    ): Result<Long> {
        val numRows = prescriptionDetailDao.updateQuantityProduct(
            productId = prescriptionDetailDto.productId!!,
            quantity = prescriptionDetailDto.quantity + 1
        )

        return if (numRows > 0) {
            Result.Success(numRows.toLong())
        } else {
            Result.Error("Failed to increase quantity")
        }
    }

    suspend fun decreaseQuantity(
        prescriptionDetailDto: PrescriptionDetailDto
    ): Result<Long> {
        if (prescriptionDetailDto.quantity > 1) {
            val numRows = prescriptionDetailDao.updateQuantityProduct(
                productId = prescriptionDetailDto.productId!!,
                quantity = prescriptionDetailDto.quantity - 1
            )

            return if (numRows > 0) {
                Result.Success(numRows.toLong())
            } else {
                Result.Error("Failed to decrease quantity")
            }
        }
        return Result.Error("Quantity must be greater than 1")
    }

    suspend fun deletePrescriptionDetailDto(prescriptionDetailDto: PrescriptionDetailDto): Result<Int> {
        val numRows = prescriptionDetailDao.deleteProduct(
            productId = prescriptionDetailDto.productId!!,
        )

        return if (numRows > 0) {
            Result.Success(numRows)
        } else {
            Result.Error("Failed to delete product")
        }
    }

    suspend fun savePrescription(): Result<String> {
        val prescriptionDto = getPrescriptionDto()
        val prescriptionDetails = prescriptionDto.data?.prescriptionDetails

        if (prescriptionDetails == null || prescriptionDetails.isEmpty()) {
            return Result.Error("No products added")
        }

        val prescription = prescriptionMapper.toEntity(prescriptionDto.data)

        return try {
            val prescriptionId = prescriptionRepo.insertPrescription(prescription)

            Result.Success(prescriptionId)
        } catch (e: Exception) {
            Result.Error("Failed to save prescription")
        }
    }

    suspend fun getPrescription(id: String): Result<Prescription> {
        return try {
            val prescription = prescriptionRepo.getPrescription(id)

            Result.Success(prescription)
        } catch (e: Exception) {
            Result.Error("Failed to get prescription")
        }
    }

    suspend fun clearLocal() {
        prescriptionDao.deleteAll()
        prescriptionDetailDao.deleteAll()
    }
}