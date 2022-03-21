package com.example.drugstore.models

import com.example.drugstore.R

data class Nutrition(
     var NutritionID:String = "",
    var NutritionName:String = "",
     var Unit:String = "mg",
){
    companion object {
        fun createListOfNutrition(): ArrayList<Nutrition> {
            val list: ArrayList<Nutrition> = arrayListOf()
            list.add(Nutrition("ID",
                "Protein","100mg"))
            list.add(Nutrition("ID",
                "Protein","100mg"))
            list.add(Nutrition("ID",
                "Protein","100mg"))
            list.add(Nutrition("ID",
                "Protein","100mg"))
            return list
        }
    }
}
