package com.example.drugstore.presentation.chat

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.presentation.adapter.ChatAdapter
import com.example.drugstore.databinding.ActivityChatBinding
import com.example.drugstore.data.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    @Inject lateinit var chatVM: ChatVM
    @Inject lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatVM.setupReceiveMess("aaaaaaaaa")

        val adapter = ChatAdapter(firebaseAuth.currentUser!!.uid)
        chatVM.chatList.observe(this){
            adapter.setItems(it)
            binding.rvContent.scrollToPosition(it.size - 1)
        }
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(this)

        binding.btnSend.setOnClickListener {
            chatVM.sendMess("aaaaaaaaa",binding.etMess.text.toString())
            binding.etMess.setText("")
        }

        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}