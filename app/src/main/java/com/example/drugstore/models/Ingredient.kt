package com.example.drugstore.models

import com.example.drugstore.R

data class Ingredient(
    val IngredientID:String = "",
    val IngredientImage:Int= 1,
   val IngredientName:String = "",
){
    var orderID:String = ""
    var repeatOrder:Boolean=false
    companion object{
        fun createListIngredient():ArrayList<Ingredient>{
            val list:ArrayList<Ingredient> = arrayListOf()
            list.add(Ingredient("2",R.drawable.trending,"Covid"))
            list.add(Ingredient("2",R.drawable.trending,"Covid"))
            list.add(Ingredient("2",R.drawable.trending,"Covid"))
            list.add(Ingredient("2",R.drawable.trending,"Covid"))
            return list
        }
    }
}

