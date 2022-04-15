package com.example.drugstore.service

import com.example.drugstore.data.repository.CategoryRepo

class CategoryService {
    fun fetchAllCategories() = CategoryRepo().fetchAllCategories()
    fun fetchCategory(id: Int) = CategoryRepo().fetchCategory(id)
}