package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.drugstore.data.models.TopicNews
import com.example.drugstore.databinding.ItemNewsTopicBinding

class NewsTopicAdapter(): RecyclerView.Adapter<NewsTopicAdapter.ProductViewHolder>() {
    private var list: List<TopicNews> = listOf()
    var onItemClick: ((TopicNews) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<TopicNews>){
        this.list = list
        notifyDataSetChanged()
    }
    class ProductViewHolder(item:ItemNewsTopicBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemNewsTopicBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = list[position]
        holder.binding.root.setOnClickListener {
            onItemClick!!.invoke(model)
        }
        holder.binding.tvNameNews.text = model.topic
        holder.binding.tvNameNews.setBackgroundColor(Color.TRANSPARENT)
        Glide.with(holder.binding.root)
            .load(model.img)
            .into(object : SimpleTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.binding.clImage.setBackgroundDrawable(resource)
                }
            })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}