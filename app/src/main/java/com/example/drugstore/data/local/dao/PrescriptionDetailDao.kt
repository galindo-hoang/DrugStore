package com.example.drugstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.models.PrescriptionDetail

@Dao
interface PrescriptionDetailDao {
    @Insert
    fun insertProduct(prescriptionDetailDto: PrescriptionDetailDto): Long

    @Query("select * from prescription_detail")
    fun fetchAllPrescriptionDetails(): List<PrescriptionDetailDto>

    @Query("update prescription_detail set quantity=:quantity where prescription_detail.productId=:productId")
    suspend fun updateQuantityProduct(quantity: Int, productId: Int): Int

    @Query("delete from prescription_detail where prescription_detail.productId=:productId")
    suspend fun deleteProduct(productId: Int)

    @Query("DELETE FROM prescription_detail")
    suspend fun deleteAll()

    @Query("select * from prescription_detail where prescription_detail.productId=:productId")
    fun fetchPrescriptionDetail(productId: Int): List<PrescriptionDetailDto>
}