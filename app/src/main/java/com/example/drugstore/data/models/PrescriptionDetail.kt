package com.example.drugstore.data.models

import androidx.room.Entity

data class PrescriptionDetail(
    val productId: Int? = null,
    val quantity: Int = 1
)