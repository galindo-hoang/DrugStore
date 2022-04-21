package com.example.drugstore.data.local.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.drugstore.data.models.Product

@Entity(tableName = "prescription_detail")
data class PrescriptionDetailDto(
    @PrimaryKey(autoGenerate = false)
    var productId: Int? = null,
    var quantity: Int = 1,

    @Ignore
    var product: Product? = null
)