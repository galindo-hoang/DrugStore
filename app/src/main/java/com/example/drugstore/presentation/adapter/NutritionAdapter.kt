package com.example.drugstore.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugstore.R
import com.example.drugstore.data.models.Nutrition

class NutritionAdapter(private val nutritiontmp: List<Nutrition>) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameNutrition = listItemView.findViewById<TextView>(R.id.nameNutrition)
       val unitNutrition=listItemView.findViewById<TextView>(R.id.unitNutrition);
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var view:View?=null
        view = inflater.inflate(R.layout.item_nutrition, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Get the data model based on position
        val nutrition: Nutrition= nutritiontmp.get(position)
// Set item views based on your views and data model
        val textView1 = holder.nameNutrition
        textView1.text=nutrition.NutritionName.toString();
        val textView2 = holder.unitNutrition;
        textView2.text=nutrition.Unit.toString()
    }

    override fun getItemCount(): Int {
        return nutritiontmp.size;
    }
}