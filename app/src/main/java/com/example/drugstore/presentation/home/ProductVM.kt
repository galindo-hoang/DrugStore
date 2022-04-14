package com.example.drugstore.presentation.home

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.ProductService

class ProductVM: ViewModel() {
    fun getAllListProducts() = ProductService().fetchAllProducts()
    fun getListProductsByCategory(id: Int) = ProductService().fetchProductsByCategory(id)
}