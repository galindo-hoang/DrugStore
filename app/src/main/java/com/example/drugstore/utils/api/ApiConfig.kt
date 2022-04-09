package com.example.drugstore.utils.api

import com.example.drugstore.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_NEWS)
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit = builder.build()
    val apiService: NewsApi = retrofit.create(NewsApi::class.java)
}