package com.example.drugstore.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.data.models.Ingredient
import com.example.drugstore.data.models.Nutrition

class NutritionAdapter(private var nutritiontmp: List<Nutrition> = listOf()) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameNutrition = listItemView.findViewById<TextView>(R.id.nameNutrition)!!
        val unitNutrition = listItemView.findViewById<TextView>(R.id.unitNutrition)!!
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Nutrition>) {
        nutritiontmp = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.item_nutrition, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nutrition: Nutrition = nutritiontmp[position]
        holder.nameNutrition.text = nutrition.NutritionName
        holder.unitNutrition.text = nutrition.Unit
    }

    override fun getItemCount(): Int {
        return nutritiontmp.size;
    }
}