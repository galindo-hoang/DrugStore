package com.example.drugstore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.databinding.ItemNotificationBinding

class NotificationAdapter(private var list:List<String>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    class NotificationViewHolder(item: ItemNotificationBinding): RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        if(position == list.size - 1){
            holder.binding.vDivider.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}