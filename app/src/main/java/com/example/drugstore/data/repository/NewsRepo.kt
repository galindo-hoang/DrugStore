package com.example.drugstore.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.drugstore.data.models.TopicNews
import com.example.drugstore.utils.api.ApiConfig
import com.google.firebase.firestore.FirebaseFirestore

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