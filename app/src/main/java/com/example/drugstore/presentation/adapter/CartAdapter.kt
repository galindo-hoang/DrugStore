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
    var onMinusClick: ((CartProduct) -> Unit)? = null
    var onAddClick: ((CartProduct) -> Unit)? = null
    inner class ViewHolder(view: ItemCartBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = proCartProduct[position]
        holder.binding.tvNameProduct.text = model.ProName
        Glide.with(holder.binding.root)
            .load(model.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivProduct)

        holder.binding.productQuantity.text = model.Quantity.toString()
        holder.binding.tvPriceProduct.text = "${DecimalFormat("##,###").format(model.Price*model.Quantity)}"+ " VNĐ"
        holder.binding.btnAdd.setOnClickListener {
            onAddClick?.invoke(model)
        }
        holder.binding.btnMinus.setOnClickListener {
            onMinusClick?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return proCartProduct.size
    }
}