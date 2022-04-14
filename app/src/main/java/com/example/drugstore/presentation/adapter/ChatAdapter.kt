package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.databinding.ItemReceiveBinding
import com.example.drugstore.databinding.ItemSendBinding
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.Chat

class ChatAdapter(private val chatList: ArrayList<Chat>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val SEND = 0
    private val RECEIVE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == SEND){
            return SendViewHolder(ItemSendBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            return ReceiveViewHolder(ItemReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = chatList[position]
        if(holder.javaClass == SendViewHolder::class.java){
            (holder as SendViewHolder).binding.tvSend.text = model.content
        }else{
            (holder as ReceiveViewHolder).binding.tvReceive.text = model.content
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseClass.getFirebaseAuth().currentUser!!.uid == chatList[position].sendID){
            SEND
        }else RECEIVE
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class SendViewHolder(item: ItemSendBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    class ReceiveViewHolder(item: ItemReceiveBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }
}