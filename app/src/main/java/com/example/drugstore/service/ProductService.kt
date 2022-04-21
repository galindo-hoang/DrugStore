package com.example.drugstore.service

import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.ProductRepo
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log
import com.example.drugstore.utils.Response

@Singleton
class ProductService @Inject constructor(
    private val productRepo: ProductRepo
) {
    suspend fun fetchAllProducts() = productRepo.fetchAllProducts()
    suspend fun fetchProductsByCategory(id: Int) = productRepo.fetchAllProductsWithCategory(id)
    suspend fun fetchProductsWithSearch(search: String) = productRepo.fetchProductsWithSearch(search)

    suspend fun countProduct() = productRepo.countProducts()

    suspend fun addProduct(product: Product) = productRepo.addProduct(product)
    suspend fun updateProduct(id: String, dataUpdate: HashMap<String, Any>) = productRepo.updateProduct(id,dataUpdate)
    suspend fun fetchPaginateProducts(
        pageSize: Long,
        firstFetch: Boolean = true
    ): Response<List<Product>> {
        return try {
            val result = ProductRepo().fetchPaginateProducts(pageSize, firstFetch)
            Response.success(result)
        } catch (e: Exception) {
            Log.d("HAGL", e.message.toString())
            Response.error(null, "Fail to fetch products")
        }
    }
}