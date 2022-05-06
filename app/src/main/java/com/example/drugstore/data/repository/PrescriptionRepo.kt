package com.example.drugstore.data.repository

import com.example.drugstore.data.models.Order
import com.example.drugstore.data.models.Prescription
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

    suspend fun getAllPrescriptions(userId: String): List<Prescription> {
        val document = collection
            .whereEqualTo("userId", userId)
            .get()
            .await()
        val result: MutableList<Prescription> = mutableListOf()
        for (i in document) result.add(i.toObject(Prescription::class.java))

        return result
    }
}