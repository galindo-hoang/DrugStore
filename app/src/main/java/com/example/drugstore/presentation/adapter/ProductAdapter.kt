package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductAdapter(
    var onItemClick: ((Product) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(item: ItemProductBinding) : RecyclerView.ViewHolder(item.root) {
        val binding = item
    }


    private var list: List<Product> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = list[position]
        holder.binding.tvDes.text = model.ProName
//        holder.binding.tvQuantity.text = "0"
        holder.binding.tvPrice.text =
            "${DecimalFormat("##,###").format(model.Price.toDouble())} VND"
        Glide
            .with(holder.binding.root)
            .load(model.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivDrug)

        holder.binding.root.setOnClickListener {
            onItemClick!!.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}