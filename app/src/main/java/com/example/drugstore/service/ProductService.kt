package com.example.drugstore.service

import android.util.Log
import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.ProductRepo
import com.example.drugstore.utils.Response
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ProductService @Inject constructor() {
    fun fetchAllProducts() = ProductRepo().fetchAllProducts()
    fun fetchProductsByCategory(id: Int) = ProductRepo().fetchAllProductsWithCategory(id)
    suspend fun fetchProductsWithSearch(search: String) =
        ProductRepo().fetchProductsWithSearch(search)

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