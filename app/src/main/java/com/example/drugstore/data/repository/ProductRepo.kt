package com.example.drugstore.data.repository

import android.util.Log
import com.example.drugstore.data.models.Product
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepo @Inject constructor() {
    private val collection = FirebaseFirestore.getInstance().collection("product")

    suspend fun fetchAllProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val result: MutableList<Product> = mutableListOf()
            val document = collection.get().await()
            for (i in document) {
                result.add(i.toObject(Product::class.java))
            }
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message.toString(), null)
        }
    }


    suspend fun fetchAllProductsWithCategory(catID: Int): Result<List<Product>> =
        withContext(Dispatchers.IO) {
            try {
                val result: MutableList<Product> = mutableListOf()
                val document = collection.whereEqualTo(Constants.CAT_ID, catID).get().await()
                for (i in document) {
                    result.add(i.toObject(Product::class.java))
                }
                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }

    suspend fun fetchProductsWithSearch(search: String): Result<List<Product>> =
        withContext(Dispatchers.IO) {
            try {
                val result: MutableList<Product> = mutableListOf()
                val documents = collection.get().await()
                for (i in documents) {
                    if (i.getString(Constants.PRODUCT_NAME)?.lowercase()
                            ?.contains(search) == true
                    ) {
                        result.add(i.toObject(Product::class.java))
                    }
                }
                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }

    suspend fun countProducts(): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val document = collection.get().await()
            Result.Success(document.size())
        } catch (e: Exception) {
            Result.Error(e.message.toString(), null)
        }
    }

    suspend fun addProduct(product: Product): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            collection.document(product.ProID.toString()).set(product).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e.message.toString(), false)
        }
    }

    suspend fun updateProduct(id: String, dataUpdate: HashMap<String, Any>): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                collection.document(id).update(dataUpdate).await()
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), false)
            }
        }

    suspend fun fetchPaginateProducts(pageSize: Long, firstFetch: Boolean = true)
            : List<Product> {
        val query = collection.orderBy("proName").limit(pageSize)
        val snapshot: QuerySnapshot = when {
            firstFetch -> {
                query.get().await()
            }
            lastQueryProductName != null -> {
                query.startAfter(lastQueryProductName)
                    .get().await()
            }
            else -> throw IllegalStateException("lastQuerySnapshot is null")
        }

        lastQueryProductName = if (snapshot.size() == 0) {
            null
        } else {
            val documentSnapshot = snapshot.documents[snapshot.size() - 1]
            documentSnapshot.toObject(Product::class.java)?.ProName
        }

        return (snapshot.toObjects(Product::class.java));
    }

    suspend fun fetchProduct(productId: Int?): Product? {
        return fetchProduct(productId.toString())
    }

    suspend fun fetchProduct(productId: String?): Product? {
        val query = collection.document(productId.toString())
        val snapshot = query.get().await()

        return snapshot.toObject(Product::class.java)
    }

    companion object {
        private var lastQueryProductName: String? = null
    }
}