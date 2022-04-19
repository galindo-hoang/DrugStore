package com.example.drugstore.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "prescription")
data class PrescriptionDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val time: String? = null
)
