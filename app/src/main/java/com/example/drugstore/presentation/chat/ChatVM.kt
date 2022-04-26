package com.example.drugstore.presentation.chat

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugstore.data.models.Chat
import com.example.drugstore.service.MessageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatVM @Inject constructor(
    private val messageService: MessageService,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    companion object{
        const val path = "ChatOrder"
    }
    private val mutableChatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>> get() = mutableChatList
    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val chatList:MutableList<Chat> = mutableListOf()
            for(i in dataSnapshot.children) i.getValue(Chat::class.java)?.let { chatList.add(it) }
            mutableChatList.postValue(chatList)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

    fun sendMess(orderID:String,mess: String) {
        viewModelScope.launch {
            firebaseAuth.currentUser?.let { Chat(it.uid,mess) }?.let {
                messageService.sendMess(path,orderID, it)
            }
        }
    }

    fun setupReceiveMess(orderID:String) = messageService.setupReceiveMess(path,orderID,valueEventListener)

    fun stopReceiveMess(orderID:String) = messageService.removeReceiveMess(path,orderID,valueEventListener)
}