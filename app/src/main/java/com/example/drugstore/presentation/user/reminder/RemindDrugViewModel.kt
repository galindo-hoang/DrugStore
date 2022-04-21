package com.example.drugstore.presentation.user.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.PrescriptionService
import com.example.drugstore.service.ProductService
import com.example.drugstore.utils.Result
import com.example.drugstore.utils.Status
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemindDrugViewModel @Inject constructor(
    @ActivityContext private val context: Context,
    private val productService: ProductService,
    private val prescriptionService: PrescriptionService
) : ViewModel() {
    private var medicines = mutableListOf<Product>()
    private val _shownMedicines = MutableLiveData<List<Product>>()
    private val pageSize = 12L
    private var firstFetch = true
    var isFetching = false

    val shownMedicines: LiveData<List<Product>>
        get() = _shownMedicines


    fun getProducts() {
        if (searchJob == null && !isFetching) {
            isFetching = true
            viewModelScope.launch(Dispatchers.IO) {
                productService.fetchPaginateProducts(pageSize, firstFetch).run {
                    if (status == Status.SUCCESS) {
                        medicines.addAll(data!!)
                        _shownMedicines.postValue(medicines)
                    }
                }
                firstFetch = false
                isFetching = false
            }
        }
    }

    fun addProduct(product: Product, back: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            prescriptionService.addProduct(product).run {
                if (this is Result.Success) {
                    withContext(Dispatchers.Main) {
                        back()
                    }
                } else if (this is Result.Error) {
                    showError(message)
                }
            }
        }
    }

    private var searchJob: Job? = null

    fun searchProduct(search: String) {
        searchJob?.cancel()
        searchJob = null

        if (search.isEmpty()) {
            _shownMedicines.postValue(medicines)
        } else {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                productService.fetchProductsWithSearch(search).run {
                    if (this is Result.Success) {
                        _shownMedicines.postValue(data!!)
                    } else {
                        showError(message)
                    }
                }
            }
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

}