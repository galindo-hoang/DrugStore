package com.example.drugstore.data.local.dao

import androidx.room.*
import com.example.drugstore.data.local.dto.PrescriptionDto
import com.example.drugstore.data.models.Prescription
import java.util.*

@Dao
interface PrescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(prescriptionDTO: PrescriptionDto): Long

    @Query(
        "UPDATE prescription SET startDate = :startDate," +
                "endDate = :endDate, minutes = :minutes, hours = :hours "
    )
    suspend fun updateProduct(
        startDate: Date,
        endDate: Date,
        minutes: Int,
        hours: Int
    ): Int

    @Query("SELECT * FROM prescription")
    suspend fun getPrescription(): List<PrescriptionDto>

    @Query("DELETE FROM prescription")
    fun deleteAll()
}