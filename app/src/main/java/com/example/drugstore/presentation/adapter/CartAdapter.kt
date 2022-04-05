package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.databinding.FragmentCartBinding

class CartAdapter(private val proCartProduct: List<CartProduct>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
//    var onItemClick: ((OrderHistory) -> Unit)? = null
    inner class ViewHolder(view: FragmentCartBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return proCartProduct.size
    }
}