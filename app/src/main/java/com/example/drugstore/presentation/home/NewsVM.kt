package com.example.drugstore.presentation.home

import androidx.lifecycle.*
import com.example.drugstore.service.SNewsRepo
import com.example.drugstore.utils.ResultAPI
import kotlinx.coroutines.Dispatchers

class NewsVM:ViewModel() {
    fun getListTopics() = SNewsRepo().fetchAllTopics()

    fun getNotesByTopicFromApi(topic:String) = liveData(Dispatchers.IO){
        emit(ResultAPI.loading(null))
        try{
            emit(ResultAPI.success(SNewsRepo().fetchAllNewsByTopic(topic)))
        }catch (ex:Exception){
            emit(ResultAPI.error(null,ex.message?: "Error"))
        }
    }

}