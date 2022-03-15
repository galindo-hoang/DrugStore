package com.example.drugstore.models

data class Product(
    private var ProID:String = "",
    private var CatID:String = "",
    private var Price:Float = 0.0f,
    private var ProName:String = "",
    private var Quantity:Int = 0,
    private var ProImage:String = "",
    private var Description:String = "",
    private var IngredientList:ArrayList<Ingredient> = arrayListOf(),
    private var NutritionList:ArrayList<Nutrition> = arrayListOf()
)
