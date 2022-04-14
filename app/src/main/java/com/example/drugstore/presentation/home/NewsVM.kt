package com.example.drugstore.presentation.home

import androidx.lifecycle.*
import com.example.drugstore.service.SNewsRepo
import com.example.drugstore.utils.Resource
import kotlinx.coroutines.Dispatchers

class NewsVM:ViewModel() {
    fun getListTopics() = SNewsRepo().fetchAllTopics()

    fun getNotesByTopicFromApi(topic:String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try{
            emit(Resource.success(SNewsRepo().fetchAllNewsByTopic(topic)))
        }catch (ex:Exception){
            emit(Resource.error(null,ex.message?: "Error"))
        }
    }

}