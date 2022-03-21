package com.example.drugstore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.models.Cart

class CartAdapter(private val proCart: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
   /* var onItemClick: ((OrderHistory) -> Unit)? = null*/
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameProduct = listItemView.findViewById<TextView>(R.id.nameProduct)
        val quantityProduct = listItemView.findViewById<TextView>(R.id.productQuantity)
        val imgProduct=listItemView.findViewById<ImageView>(R.id.imgProduct)
        val priceProduct=listItemView.findViewById<TextView>(R.id.priceProduct)
      /*  init {
            listItemView.setOnClickListener { onItemClick?.invoke(orderHistoris[adapterPosition])
            }
        }*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var view:View?=null
        view = inflater.inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Get the data model based on position
        val cart: Cart = proCart.get(position)
// Set item views based on your views and data model
        val textView1 = holder.imgProduct
        textView1.setImageResource(cart.img)
        val textView2 = holder.quantityProduct
        textView2.text=cart.Quantity.toString()
        val textView3=holder.priceProduct
        textView3.text=cart.Total.toString().plus("VND")
        val textView4=holder.nameProduct
        textView4.text=cart.proName.toString()
    }

    override fun getItemCount(): Int {
        return proCart.size
    }
}