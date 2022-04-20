package com.example.drugstore.presentation.home

import androidx.lifecycle.*
import com.example.drugstore.service.NewsService
import com.example.drugstore.utils.Response
import kotlinx.coroutines.Dispatchers

class NewsVM:ViewModel() {
    fun getListTopics() = NewsService().fetchAllTopics()

    fun getNotesByTopicFromApi(topic:String) = liveData(Dispatchers.IO){
        emit(Response.loading(null))
        try{
            emit(Response.success(NewsService().fetchAllNewsByTopic(topic)))
        }catch (ex:Exception){
            emit(Response.error(null,ex.message?: "Error"))
        }
    }

}