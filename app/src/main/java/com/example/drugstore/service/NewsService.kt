package com.example.drugstore.service

import com.example.drugstore.data.repository.NewsRepo

class NewsService {
    fun fetchAllTopics() = NewsRepo().fetchAllTopic()
    suspend fun fetchAllNewsByTopic(topic: String) = NewsRepo().fetchNewsByTopicFromApi(topic)
}