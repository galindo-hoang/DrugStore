package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.TopicNews
import com.example.drugstore.databinding.ItemTopicBinding

class ButtonTopicAdapter: RecyclerView.Adapter<ButtonTopicAdapter.ButtonTopicViewHolder>() {
    class ButtonTopicViewHolder(view:ItemTopicBinding):RecyclerView.ViewHolder(view.root){
        val binding = view
    }

    var onItemClick: ((TopicNews) -> Unit)? = null

    private var list: MutableList<TopicNews> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TopicNews>){
        this.list = list as MutableList<TopicNews>
        list.add(0, TopicNews("",""))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonTopicViewHolder {
        return ButtonTopicViewHolder(ItemTopicBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ButtonTopicViewHolder, position: Int) {
        val model = list[position]

        if(model.topic == "") holder.binding.btnNews.text = "all"
        else holder.binding.btnNews.text = model.topic

        holder.binding.btnNews.setOnClickListener {
            onItemClick?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}