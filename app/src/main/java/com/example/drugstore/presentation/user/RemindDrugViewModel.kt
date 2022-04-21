package com.example.drugstore.presentation.user

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.PrescriptionService
import com.example.drugstore.service.ProductService
import com.example.drugstore.utils.Status
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemindDrugViewModel @Inject constructor(
    @ActivityContext private val context: Context,
    private val productService: ProductService,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private var medicines = listOf<Product>()
    private val _shownMedicines = MutableLiveData<List<Product>>()
    private val pageSize = 12L
    private var firstFetch = true

    val shownMedicines: LiveData<List<Product>>
        get() = _shownMedicines


    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productService.fetchPaginateProducts(pageSize, firstFetch).run {
                if (status == Status.SUCCESS) {
                    medicines = medicines + data!!
                    _shownMedicines.postValue(medicines)
                }
            }
            firstFetch = false
        }
    }


    private fun showError(message: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Error: ${message.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun addProduct(product: Product, back: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.addProduct(product).run {
                if (status == Status.SUCCESS) {
                    withContext(Dispatchers.Main) {
                        back()
                    }
                } else if (status == Status.ERROR) {
                    showError(message)
                }
            }
        }
    }
}