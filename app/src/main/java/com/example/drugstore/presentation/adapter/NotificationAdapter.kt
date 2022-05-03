package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.Notification
import com.example.drugstore.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    class NotificationViewHolder(item: ItemNotificationBinding): RecyclerView.ViewHolder(item.root){
        val binding = item
    }
    private var list:List<Notification> = listOf()
    var onItemClick: ((Notification) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Notification>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        if(position == list.size - 1){
            holder.binding.vDivider.visibility = View.INVISIBLE
        }
        val model = list[position]

        Glide.with(holder.binding.root)
            .load(model.largeIcon)
            .placeholder(R.drawable.ic_ordered)
            .into(holder.binding.ivNotify)
        holder.binding.tvContent.text = model.body
        holder.binding.tvTitle.text = model.title
        holder.binding.tvDate.text = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(model.time)

        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}