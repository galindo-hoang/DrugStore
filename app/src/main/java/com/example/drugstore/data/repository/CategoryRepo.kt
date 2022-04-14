package com.example.drugstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.Category
import com.google.firebase.firestore.FirebaseFirestore

class CategoryRepo {
    private val collection = FirebaseFirestore.getInstance().collection("category")

    fun fetchAllCategories(): MutableLiveData<List<Category>>{
        val value = mutableListOf<Category>()
        val result: MutableLiveData<List<Category>> = MutableLiveData()
        collection
            .get()
            .addOnSuccessListener {
                for(i in it.documents){
                    i.toObject(Category::class.java)?.let { it1 -> value.add(it1) }
                }
                result.postValue(value)
            }
        return result
    }

    fun fetchCategory(id: Int): MutableLiveData<Category> {
        val result: MutableLiveData<Category> = MutableLiveData()
        collection
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                result.postValue(it.toObject(Category::class.java))
            }
        return result
    }
}