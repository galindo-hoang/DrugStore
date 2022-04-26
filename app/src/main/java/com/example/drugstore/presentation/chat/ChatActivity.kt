package com.example.drugstore.presentation.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.ActivityChatBinding
import com.example.drugstore.presentation.adapter.ChatAdapter
import com.example.drugstore.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var orderID:String? = null
    @Inject lateinit var chatVM: ChatVM
    @Inject lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(Constants.ORDER_ID)) orderID = intent.getStringExtra(Constants.ORDER_ID)

        orderID?.let { chatVM.setupReceiveMess(it) }

        val adapter = ChatAdapter(firebaseAuth.currentUser!!.uid)
        chatVM.chatList.observe(this){
            adapter.setItems(it)
            binding.rvContent.scrollToPosition(it.size - 1)
        }
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(this)

        binding.btnSend.setOnClickListener {
            if(binding.etMess.text.toString().trim() != ""){
                orderID?.let { it1 -> chatVM.sendMess(it1,binding.etMess.text.toString()) }
                binding.etMess.setText("")
            }else Toast.makeText(this,"Please fill mess",Toast.LENGTH_SHORT).show()
        }

        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}