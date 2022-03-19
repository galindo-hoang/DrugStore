package com.example.drugstore.models

data class Message(
    val oderID:String = "",
    var arrayChat: ArrayList<Chat> = arrayListOf()
)
