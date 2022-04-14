package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.data.models.OrderHistory

class OrderAdapter(private val orderHistoris: List<OrderHistory>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    var onItemClick: ((OrderHistory) -> Unit)? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val addressOrderHistory = listItemView.findViewById<TextView>(R.id.addressOrderHis)
        val quantityOrderHistory = listItemView.findViewById<TextView>(R.id.quantityOrderHis)
        val orderHistory=listItemView.findViewById<ImageView>(R.id.imgViewOrderHistory)
        val totalOrderHistory=listItemView.findViewById<TextView>(R.id.totalOrderhis)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(orderHistoris[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var view:View?=null
        view = inflater.inflate(R.layout.item_order, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Get the data model based on position
        val orderHistory: OrderHistory = orderHistoris.get(position)
// Set item views based on your views and data model
        val textView1 = holder.orderHistory
        textView1.setImageResource(orderHistory.img)
        val textView2 = holder.quantityOrderHistory
        textView2.setText(orderHistory.quantity.toString())
        val textView3 = holder.addressOrderHistory
        textView3.setText(orderHistory.address)
        val textView4=holder.totalOrderHistory
        textView4.setText(orderHistory.total.toString().plus(" VND"))
    }

    override fun getItemCount(): Int {
        return orderHistoris.size
    }
}