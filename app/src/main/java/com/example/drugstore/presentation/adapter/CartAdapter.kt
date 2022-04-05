package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.databinding.ItemCartBinding
import java.text.DecimalFormat

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var proCartProduct: List<CartProduct> = listOf()

    fun setList(list: List<CartProduct>){
        proCartProduct = list
        notifyDataSetChanged()
    }
//    var onItemClick: ((OrderHistory) -> Unit)? = null
    inner class ViewHolder(view: ItemCartBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = proCartProduct[position]
        holder.binding.nameProduct.text = model.ProName
        Glide.with(holder.binding.root)
            .load(model.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgProduct)

        holder.binding.productQuantity.text = model.Quantity.toString()
        holder.binding.priceProduct.text = "${DecimalFormat("##,###").format(model.Price*model.Quantity)}"
    }

    override fun getItemCount(): Int {
        return proCartProduct.size
    }
}