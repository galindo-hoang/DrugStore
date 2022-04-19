package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.data.models.Order
import com.example.drugstore.databinding.ItemOrderBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderAdapter() : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    var onItemClick: ((Order) -> Unit)? = null
    private var orderHistory: List<Order> = listOf()
    class OrderViewHolder(view: ItemOrderBinding) : RecyclerView.ViewHolder(view.root) {
        val binding = view
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<Order>){
        this.orderHistory = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val model = orderHistory[position]
        holder.binding.tvDate.text = SimpleDateFormat("dd MMMM, HH:mm").format(model.DateOrder)
        var sum = 0
        model.ProductList.forEach {
            sum += it.Quantity*it.Price
        }
        holder.binding.tvPrice.text = "${DecimalFormat("##,###").format(sum)} VND"
        holder.binding.tvStatus.text = when(model.Status){
            false -> "Đang vận chuyển"
            else -> "Giao thành công"
        }

        holder.binding.ivOrder.setImageResource(
            when(model.Status){
                false -> R.drawable.ic_wait_shipping_24
                else -> R.drawable.ic_done_shipping_24
            }
        )
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return orderHistory.size
    }
}