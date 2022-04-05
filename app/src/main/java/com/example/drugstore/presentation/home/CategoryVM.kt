package com.example.drugstore.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugstore.data.models.Category
import com.example.drugstore.service.SCategoryRepo

class CategoryVM: ViewModel() {
    fun fetchAllCategories() = SCategoryRepo().fetchAllCategories()
    fun fetchCategory(id:Int) = SCategoryRepo().fetchCategory(id)
}