package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.ItemCategoryBinding
import com.example.drugstore.data.models.Category

class CategoryAdapter(
    var onItemClick: ((Category) -> Unit)? = null
): RecyclerView.Adapter<CategoryAdapter.ProductViewHolder>() {

    private var list: List<Category> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Category>){
        this.list = list
        notifyDataSetChanged()
    }

    class ProductViewHolder(item:ItemCategoryBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = list[position]
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(model)
        }
        Glide
            .with(holder.binding.root)
            .load(model.CatImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivCategory)
        holder.binding.tvName.text = model.CatName
    }

    override fun getItemCount(): Int {
        return list.size
    }
}