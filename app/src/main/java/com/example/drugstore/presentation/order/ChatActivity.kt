package com.example.drugstore.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.presentation.adapter.ChatAdapter
import com.example.drugstore.databinding.ActivityChatBinding
import com.example.drugstore.data.models.Chat

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val chat:ArrayList<Chat> = arrayListOf()
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello qwoie oiqew joibq"))
        chat.add(Chat(sendID = "1","hello"))
        chat.add(Chat(sendID = "1","hello oiqwhe obaod jojasd"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello world"))
        chat.add(Chat(sendID = "1","world hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hi thanks"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello qwoie oiqew joibq"))
        chat.add(Chat(sendID = "1","hello"))
        chat.add(Chat(sendID = "1","hello oiqwhe obaod jojasd"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello world"))
        chat.add(Chat(sendID = "1","world hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hi thanks"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello qwoie oiqew joibq"))
        chat.add(Chat(sendID = "1","hello"))
        chat.add(Chat(sendID = "1","hello oiqwhe obaod jojasd"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello world"))
        chat.add(Chat(sendID = "1","world hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hello"))
        chat.add(Chat(sendID = "oZSqjneYCoclWFuXvYdDuG1SstM2","hi thanks"))
        val adapter = ChatAdapter(chat)
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(this)

        binding.etMess.setOnClickListener {
            binding.rvContent.scrollToPosition(chat.size - 1)
        }
    }
}