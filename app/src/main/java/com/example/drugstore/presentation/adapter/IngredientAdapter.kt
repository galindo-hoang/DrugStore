package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.data.models.Ingredient

class IngredientAdapter(private val ingredient: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val imgIngredient=listItemView.findViewById<ImageView>(R.id.imgIngredient)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var view:View?=null
        view = inflater.inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Get the data model based on position
        val cart: Ingredient = ingredient.get(position)
// Set item views based on your views and data model
        val textView1 = holder.imgIngredient
//        textView1.setImageResource(cart.IngredientImage)
    }

    override fun getItemCount(): Int {
        return ingredient.size;
    }
}