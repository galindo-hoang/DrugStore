package com.example.drugstore.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("prescription")
}