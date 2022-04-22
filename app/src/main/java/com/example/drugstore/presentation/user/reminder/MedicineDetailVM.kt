package com.example.drugstore.presentation.user.reminder

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.drugstore.R
import com.example.drugstore.data.models.Product
import com.example.drugstore.service.ProductService
import javax.inject.Inject
import com.example.drugstore.utils.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.*

class MedicineDetailVM @Inject constructor(
    @ActivityContext private val context: Context,
    private val productService: ProductService
) : ViewModel() {
    private val _medicine: MutableLiveData<Product> = MutableLiveData()
    private val _productImage: MutableLiveData<RequestBuilder<Drawable>> = MutableLiveData()

    val medicine: LiveData<Product>
        get() = _medicine
    val productImage: LiveData<RequestBuilder<Drawable>>
        get() = _productImage

    fun getMedicine(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productService.fetchProduct(id, includeAll = true).run {
                if (this is Result.Success) {
                    _medicine.postValue(data!!)
                    loadImage(data.ProImage)
                } else {
                    showError(message)
                }
            }
        }
    }

    private fun loadImage(image: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val loadedImage =
                Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.ic_launcher_foreground)
            _productImage.postValue(loadedImage)
        }
    }

    private suspend fun showError(message: String?) {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Error: ${message.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}