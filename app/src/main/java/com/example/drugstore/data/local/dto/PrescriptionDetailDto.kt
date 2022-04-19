package com.example.drugstore.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prescription_detail")
data class PrescriptionDetailDto(
    @PrimaryKey(autoGenerate = false)
    val productId: Int? = null,
    val quantity: Int = 1
)