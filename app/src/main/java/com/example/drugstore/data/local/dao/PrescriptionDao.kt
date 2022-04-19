package com.example.drugstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.drugstore.data.local.dto.PrescriptionDto

@Dao
interface PrescriptionDao {
    @Insert
    suspend fun insertProduct(prescriptionDTO: PrescriptionDto)
}