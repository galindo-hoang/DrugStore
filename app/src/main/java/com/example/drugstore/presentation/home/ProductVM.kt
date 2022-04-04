package com.example.drugstore.presentation.home

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.SProductRepo

class ProductVM: ViewModel() {
    fun getAllListProducts() = SProductRepo().fetchAllProducts()
    fun getListProductsByCategory(id: Int) = SProductRepo().fetchProductsByCategory(id)
}