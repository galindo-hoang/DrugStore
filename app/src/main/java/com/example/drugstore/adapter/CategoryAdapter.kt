package com.example.drugstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.databinding.ItemCategoryBinding

class CategoryAdapter(private val list: List<String>): RecyclerView.Adapter<CategoryAdapter.ProductViewHolder>() {
    class ProductViewHolder(item:ItemCategoryBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return list.size
    }
}