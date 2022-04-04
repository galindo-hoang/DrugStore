package com.example.drugstore.data.models

data class Cart(
    var Total:Float = 0.0f,
    var img:Int=0,
    // <ProductID,Quantity>
    var ProductList: HashMap<Int,Int> = hashMapOf()
)