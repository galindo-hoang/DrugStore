package com.example.drugstore.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.News
import com.example.drugstore.data.models.TopicNews
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.api.ApiConfig
import com.example.drugstore.utils.api.NewsApi
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=54db42e432d84365af90fcde5c12c8ff
class NewsRepo {

    private val collection = FirebaseFirestore.getInstance().collection("news")

    fun fetchAllTopic(): MutableLiveData<List<TopicNews>>{
        val result:MutableLiveData<List<TopicNews>> = MutableLiveData()
        collection
            .get()
            .addOnSuccessListener {
                val model = mutableListOf<TopicNews>()
                for(i in it.documents){
                    i.toObject(TopicNews::class.java)?.let { it1 -> model.add(it1) }
                }
                result.postValue(model)
            }
        return result
    }

    suspend fun fetchNewsByTopicFromApi(topic: String) = ApiConfig.apiService.getNews(topic = topic)
}