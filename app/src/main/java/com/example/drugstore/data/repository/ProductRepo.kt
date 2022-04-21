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
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("product")

    suspend fun fetchAllProducts(): Result<List<Product>> = withContext(Dispatchers.IO){
        try {
            val result:MutableList<Product> = mutableListOf()
            val document = collection.get().await()
            for(i in document){
                result.add(i.toObject(Product::class.java))
            }
            Result.Success(result)
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }


    suspend fun fetchAllProductsWithCategory(catID: Int):Result<List<Product>> = withContext(Dispatchers.IO){
        try {
            val result:MutableList<Product> = mutableListOf()
            val document = collection.whereEqualTo(Constants.CAT_ID,catID).get().await()
            for(i in document){
                result.add(i.toObject(Product::class.java))
            }
            Result.Success(result)
        }catch (e:Exception){
            Result.Error(e.message.toString(),null)
        }
    }

    suspend fun fetchProductsWithSearch(search: String): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val result: MutableList<Product> = mutableListOf()
            val documents = collection.get().await()
            for (i in documents) {
                if (i.getString(Constants.PRODUCT_NAME)?.lowercase()?.contains(search) == true) {
                    result.add(i.toObject(Product::class.java))
                }
            }
            Result.Success(result)
        } catch (e:Exception){
            Result.Error(e.message.toString(), null)
        }
    }

    suspend fun countProducts(): Result<Int> = withContext(Dispatchers.IO){
        try {
            val document = collection.get().await()
            Result.Success(document.size())
        }catch (e:Exception){
            Result.Error(e.message.toString(), null)
        }
    }

    suspend fun addProduct(product: Product): Result<Boolean> = withContext(Dispatchers.IO){
        try {
            collection.document(product.ProID.toString()).set(product).await()
            Result.Success(true)
        }catch (e:Exception) {
            Result.Error(e.message.toString(),false)
        }
    }

    suspend fun updateProduct(id: String, dataUpdate: HashMap<String, Any>): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            collection.document(id).update(dataUpdate).await()
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(e.message.toString(),false)
        }
    }
}