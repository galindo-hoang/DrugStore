package com.example.drugstore.utils.api

import com.example.drugstore.data.models.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country:String = "us",
        @Query("category") category:String = "health",
        @Query("q") topic:String,
        @Query("apiKey") apiKey:String = "54db42e432d84365af90fcde5c12c8ff",
    ) : News
}