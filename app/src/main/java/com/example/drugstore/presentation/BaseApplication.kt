package com.example.drugstore.presentation

import android.app.Application
import com.example.drugstore.data.local.CartProductDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    val cartProductDatabase: CartProductDatabase by lazy{
        CartProductDatabase.getInstance(this)
    }
}