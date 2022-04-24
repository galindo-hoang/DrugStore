package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.Article
import com.example.drugstore.databinding.ItemNewsBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: ItemNewsBinding): RecyclerView.ViewHolder(view.root){
        val binding = view
    }

    var onItemClick: ((Article) -> Unit)? = null

    private var list: MutableList<Article> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Article>){
        this.list = list as MutableList<Article>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val model = list[position]
        holder.binding.tvTitle.text = model.title
        holder.binding.tvSource.text = model.source.name
        Glide.with(holder.binding.root)
            .load(model.urlToImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivNews)
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}