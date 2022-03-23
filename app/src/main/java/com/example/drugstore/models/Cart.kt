package com.example.drugstore.models

import com.example.drugstore.R

data class Cart(
    var Total:Float = 0.0f,
    var img:Int=0,
    // <ProductID,Quantity>
    var ProductList: HashMap<Int,Int> = hashMapOf()
)