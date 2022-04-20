package com.example.drugstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drugstore.data.local.dto.PrescriptionDto
import java.util.*

@Dao
interface PrescriptionDao {
    @Insert
    suspend fun insertProduct(prescriptionDTO: PrescriptionDto)

    @Query(
        "UPDATE prescription SET startDate = :startDate," +
                "endDate = :endDate, minutes = :minutes, hours = :hours "
    )
    suspend fun updateProduct(
        startDate: Date,
        endDate: Date,
        minutes: Int,
        hours: Int
    )
}