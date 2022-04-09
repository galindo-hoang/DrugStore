package com.example.drugstore.data.models

data class News(
    val status:String = "",
    val totalResult:Int = 0,
    val articles: ArrayList<Article> = arrayListOf()
)