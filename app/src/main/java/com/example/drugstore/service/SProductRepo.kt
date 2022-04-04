package com.example.drugstore.service

import com.example.drugstore.data.repository.ProductRepo

class SProductRepo {
    fun fetchAllProducts() = ProductRepo().fetchAllProducts()
    fun fetchProductsByCategory(id: Int) = ProductRepo().fetchAllProductsWithCategory(id)
}