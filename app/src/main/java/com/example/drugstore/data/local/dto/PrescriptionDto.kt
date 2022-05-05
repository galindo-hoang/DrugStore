package com.example.drugstore.data.local.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "prescription")
data class PrescriptionDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    var name: String = "",
    var startDate: Date = Date(),
    var endDate: Date = Date(),
    var minutes: Int = 0,
    var hours: Int = 0,

    @Ignore
    var prescriptionDetails: List<PrescriptionDetailDto> = emptyList()
)
