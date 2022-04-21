package com.example.drugstore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.drugstore.data.local.converter.DateConverter
import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.local.dao.PrescriptionDetailDao
import com.example.drugstore.data.local.dto.PrescriptionDetailDto
import com.example.drugstore.data.local.dto.PrescriptionDto

@Database(entities = [PrescriptionDto::class, PrescriptionDetailDto::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun prescriptionDao(): PrescriptionDao
    abstract fun prescriptionDetailDao(): PrescriptionDetailDao
}