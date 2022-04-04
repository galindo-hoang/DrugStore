package com.example.drugstore.service

import com.example.drugstore.data.repository.CategoryRepo

class SCategoryRepo {
    fun fetchAllCategories() = CategoryRepo().fetchAllCategory()
}