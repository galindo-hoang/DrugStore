package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.databinding.ItemProductOrderBinding
import java.text.DecimalFormat

class OrderProductAdapter: RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>()  {
    var list: List<CartProduct> = listOf()

    class OrderProductViewHolder(view: ItemProductOrderBinding): RecyclerView.ViewHolder(view.root){
        val binding = view
    }

    val getChatList: List<CartProduct> get() = list

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<CartProduct>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        return OrderProductViewHolder(ItemProductOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        val model = list[position]
        if(position == list.size - 1) holder.binding.glItem.visibility = View.INVISIBLE
        holder.binding.tvNameProduct.text = model.ProName
        holder.binding.tvPrice.text = "${DecimalFormat("##,###").format(model.Price)} x ${model.Quantity}"
        Glide.with(holder.binding.root)
            .load(model.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivProduct)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}