package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.Product
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductRepo {
    private val collection = FirebaseFirestore.getInstance().collection("product")

    fun fetchAllProducts(): MutableLiveData<List<Product>>{
        val result: MutableLiveData<List<Product>> = MutableLiveData()
        collection
            .get()
            .addOnSuccessListener {
                val value = mutableListOf<Product>()
                for(i in it.documents){
                    i.toObject(Product::class.java)?.let { it1 -> value.add(it1) }
                }
                result.postValue(value)
            }
            .addOnFailureListener {
                Log.e("---","fetchAllProducts fail")
            }
        return result
    }
    fun fetchAllProductsWithCategory(catID: Int): MutableLiveData<List<Product>>{
        val result: MutableLiveData<List<Product>> = MutableLiveData()
        collection
            .whereEqualTo(Constants.CAT_ID,catID)
            .get()
            .addOnSuccessListener {
                val value = mutableListOf<Product>()
                for(i in it.documents){
                    i.toObject(Product::class.java)?.let { it1 -> value.add(it1) }
                }
                result.postValue(value)
            }
            .addOnFailureListener {
                Log.e("---","fetchAllProductsWithCategory fail")
            }
        return result
    }

    suspend fun fetchProductsWithSearch(search: String): Result<List<Product>> = withContext(Dispatchers.IO) {
        val result:MutableList<Product> = mutableListOf()
        val documents = collection.get().await()
        for (i in documents){
            if(i.getString(Constants.PRODUCT_NAME)?.lowercase()?.contains(search) == true){
                result.add(i.toObject(Product::class.java))
            }
        }
        if(result.size == 0) Result.Error("Cant get data",null)
        else Result.Success(result)
    }
}