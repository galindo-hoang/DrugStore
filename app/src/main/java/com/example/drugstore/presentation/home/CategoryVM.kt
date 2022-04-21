package com.example.drugstore.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.service.CategoryService
import com.example.drugstore.utils.Result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

class CategoryVM @Inject constructor(
    private val categoryService: CategoryService
): ViewModel() {
    fun getAllCategories() = liveData(Dispatchers.IO){
        when(val result = categoryService.fetchAllCategories()){
            is Result.Success -> emit(result.data)
            else -> {
                Log.e("ViewModel--Category",result.message.toString())
                emit(null)
            }
        }
    }
    fun getCategory(id:Int) = liveData(Dispatchers.IO) {
        when (val result = categoryService.fetchCategory(id)) {
            is Result.Success -> emit(result.data)
            else -> {
                Log.e("ViewModel--Category", result.message.toString())
                emit(null)
            }
        }
    }
}