package com.example.drugstore.service

import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.ProductRepo
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log
import com.example.drugstore.data.repository.CategoryRepo
import com.example.drugstore.utils.Response
import com.example.drugstore.utils.Result

@Singleton
class ProductService @Inject constructor(
    private val productRepo: ProductRepo,
    private val categoryRepo: CategoryRepo
) {
    suspend fun fetchAllProducts() = productRepo.fetchAllProducts()
    suspend fun fetchProductsByCategory(id: Int) = productRepo.fetchAllProductsWithCategory(id)
    suspend fun fetchProductsWithSearch(search: String) =
        productRepo.fetchProductsWithSearch(search)

    suspend fun countProduct() = productRepo.countProducts()

    suspend fun addProduct(product: Product) = productRepo.addProduct(product)
    suspend fun updateProduct(id: String, dataUpdate: HashMap<String, Any>) =
        productRepo.updateProduct(id, dataUpdate)

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

    suspend fun fetchProduct(
        productId: Int,
        includeAll: Boolean = false
    ): Result<Product> {
        return try {
            val product = productRepo.fetchProduct(productId)

            if (product == null) {
                Result.Error("Product not found")
            } else {
                if (includeAll) {
                    val category = categoryRepo.fetchCategory(product.CatID).data!!

                    product.category = category
                }

                Result.Success(product)
            }
        } catch (e: Exception) {
            Log.d("HAGL", e.message.toString())
            Result.Error("Fail to fetch product")
        }
    }
}