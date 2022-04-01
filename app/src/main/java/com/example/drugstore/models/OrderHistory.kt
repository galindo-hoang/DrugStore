package com.example.drugstore.models

import com.example.drugstore.R

data class OrderHistory(
     var orderID:Int = 0,
     var dateOrder:String = "",
     var total:Float = 0.0f,
     var proName:String = "",
     var quantity:Int = 0,
     var status:String = "",
     var img:Int=0,
     var address:String=""
)
