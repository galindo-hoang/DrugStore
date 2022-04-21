package com.example.drugstore.service

import com.example.drugstore.data.repository.CategoryRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryService @Inject constructor(
    private val categoryRepo: CategoryRepo
) {
    suspend fun fetchAllCategories() = categoryRepo.fetchAllCategories()
    suspend fun fetchCategory(id: Int) = categoryRepo.fetchCategory(id)
}