package com.example.drugstore.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import java.util.*

data class Prescription(
    @Exclude
    var prescriptionId: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val time: String? = null,
    val prescriptionDetails: List<PrescriptionDetail>? = null,
)
