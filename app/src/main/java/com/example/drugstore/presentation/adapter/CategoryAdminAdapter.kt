package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.Category
import com.example.drugstore.databinding.ItemAdminCategoryBinding

class CategoryAdminAdapter(
    var onItemClick: ((Category) -> Unit)? = null
): RecyclerView.Adapter<CategoryAdminAdapter.ProductViewHolder>() {
    private var list: List<Category> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Category>){
        this.list = list
        notifyDataSetChanged()
    }


    class ProductViewHolder(item:ItemAdminCategoryBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemAdminCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    var rowIndex:Int?=0
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = list[position]
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(model)
            rowIndex=position
            notifyDataSetChanged()
        }
        if(rowIndex==position)
            holder.binding.nameCate.setTextColor(Color.GRAY)
        else
        {
            holder.binding.nameCate.setTextColor(Color.parseColor("#0C90F1"))
        }
        holder.binding.nameCate.text = model.CatName.toString().trim()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}