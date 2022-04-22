package com.example.drugstore.data.models

import androidx.room.Entity
import com.google.firebase.firestore.Exclude

data class PrescriptionDetail(
    val productId: Int? = null,
    val quantity: Int = 1,

    @JvmField
    @Exclude
    var product: Product? = null
)