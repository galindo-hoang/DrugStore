package com.example.drugstore.data.models

import com.google.firebase.firestore.Exclude
import java.util.*

data class Prescription(
    @Exclude @JvmField
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val time: Map<String, Int>? = null,
    var prescriptionDetails: List<PrescriptionDetail>? = null,
)
