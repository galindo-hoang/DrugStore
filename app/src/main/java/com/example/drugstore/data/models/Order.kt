package com.example.drugstore.data.models

import java.util.*
import kotlin.collections.HashMap

data class Order(
    var OrderID:Int = 0,
    var UserID:String = "",
    var DateOrder:Date = Date(),
    var ShipperID:String = "",
    // false : on Shipping
    // true : Done
    var Status:Boolean = false,
    var Address:String = "",
    var Point:Float = 0.0f,
    // 0 : cash
    // 1 : wallet online
    // 2 : bank
    var PaymentID:Int = 0,
    var DateReceive:Date = Date(),
    // <ProductID,Quantity>
    var ProductList: HashMap<Int,Int> = hashMapOf()
)
