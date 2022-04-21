package com.example.drugstore.service

import com.example.drugstore.data.models.Product
import com.example.drugstore.data.repository.ProductRepo
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

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
}