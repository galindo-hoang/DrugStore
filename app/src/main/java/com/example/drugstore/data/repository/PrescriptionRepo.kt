package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Prescription
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance()
        .collection("prescription")

    suspend fun insertPrescription(prescription: Prescription): String {
        val document = collection.add(prescription).await()

        return document.id
    }

    suspend fun getPrescription(id: String): Prescription {
        val document = collection.document(id).get().await()

        val prescription = document.toObject(Prescription::class.java)!!
            .apply {
                this.id = document.id
            }

        return prescription
    }
}