package com.example.drugstore.presentation.home

import androidx.lifecycle.ViewModel
import com.example.drugstore.service.CategoryService

class CategoryVM: ViewModel() {
    fun getAllCategories() = CategoryService().fetchAllCategories()
    fun getCategory(id:Int) = CategoryService().fetchCategory(id)
}