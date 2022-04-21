package com.example.drugstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.Category
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("category")

    suspend fun fetchAllCategories(): Result<List<Category>> = withContext(Dispatchers.IO){
        try {
            val result:MutableList<Category> = mutableListOf()
            val documents = collection.get().await()
            for(i in documents) result.add(i.toObject(Category::class.java))
            Result.Success(result)
        }catch (e:Exception) {
            Result.Error(e.message.toString(), null)
        }
    }

    suspend fun fetchCategory(id: Int): Result<Category?> = withContext(Dispatchers.IO) {
        try {
            val document = collection.document(id.toString()).get().await()
            Result.Success(document.toObject(Category::class.java))
        }catch (e: Exception){
            Result.Error(e.message.toString(),null)
        }
    }
}