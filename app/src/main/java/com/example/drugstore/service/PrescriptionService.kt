package com.example.drugstore.service

import android.util.Log
import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.local.dao.PrescriptionDetailDao
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.mapper.PrescriptionMapper
import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.PrescriptionRepo
import com.example.drugstore.data.repository.ProductRepo
import com.example.drugstore.utils.Response
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
    suspend fun getPrescription(): Response<PrescriptionDto> {
        val prescription = prescriptionDao.getPrescription()

        return if (prescription.isEmpty()) {
            Response.error(null, "No prescription found")
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

            Response.success(result)
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

    suspend fun addProduct(product: Product): Response<Int> {
        var result = Response.success(1)

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
            result = Response.error(null, "Failed to add product")
        }

        return result
    }

    suspend fun increaseQuantity(
        prescriptionDetailDto: PrescriptionDetailDto
    ): Response<Long> {
        val numRows = prescriptionDetailDao.updateQuantityProduct(
            productId = prescriptionDetailDto.productId!!,
            quantity = prescriptionDetailDto.quantity + 1
        )

        return if (numRows > 0) {
            Response.success(numRows.toLong())
        } else {
            Response.error(null, "Failed to increase quantity")
        }
    }

    suspend fun decreaseQuantity(
        prescriptionDetailDto: PrescriptionDetailDto
    ): Response<Long> {
        if (prescriptionDetailDto.quantity > 1) {
            val numRows = prescriptionDetailDao.updateQuantityProduct(
                productId = prescriptionDetailDto.productId!!,
                quantity = prescriptionDetailDto.quantity - 1
            )

            return if (numRows > 0) {
                Response.success(numRows.toLong())
            } else {
                Response.error(null, "Failed to decrease quantity")
            }
        }
        return Response.error(null, "Quantity must be greater than 1")
    }
}