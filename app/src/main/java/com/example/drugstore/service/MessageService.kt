package com.example.drugstore.service

import com.example.drugstore.data.models.Chat
import com.example.drugstore.data.repository.MessageRepo
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageService @Inject constructor(
    private val messageRepo: MessageRepo
) {
    suspend fun sendMess(path:String, orderID:String, chat: Chat) = messageRepo.sendMess(path,orderID,chat)
    fun setupReceiveMess(path:String, orderID:String, valueEventListener: ValueEventListener) = messageRepo.setupReceiveMess(path,orderID, valueEventListener)
    fun removeReceiveMess(path:String, orderID:String,valueEventListener: ValueEventListener) = messageRepo.removeReceiveMess(path,orderID, valueEventListener)
}