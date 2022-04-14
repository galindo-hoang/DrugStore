package com.example.drugstore.presentation.home

import androidx.lifecycle.*
import com.example.drugstore.service.NewsService
import com.example.drugstore.utils.Respond
import kotlinx.coroutines.Dispatchers

class NewsVM:ViewModel() {
    fun getListTopics() = NewsService().fetchAllTopics()

    fun getNotesByTopicFromApi(topic:String) = liveData(Dispatchers.IO){
        emit(Respond.loading(null))
        try{
            emit(Respond.success(NewsService().fetchAllNewsByTopic(topic)))
        }catch (ex:Exception){
            emit(Respond.error(null,ex.message?: "Error"))
        }
    }

}