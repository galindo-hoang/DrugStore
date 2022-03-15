package com.example.drugstore.models

import java.util.*
import kotlin.collections.HashMap

data class Order(
    private var OrderID:String = "",
    private var UserID:String = "",
    private var DateOrder:Date = Date(),
    private var ShipperID:String = "",
    // false : on Shipping
    // true : Done
    private var Status:Boolean = false,
    private var Address:String = "",
    private var Point:Float = 0.0f,
    // 0 : cash
    // 1 : wallet online
    // 2 : bank
    private var PaymentID:Int = 0,
    private var DateReceive:Date = Date(),
    // <ProductID,Quantity>
    private var ProductList: HashMap<String,Int> = hashMapOf()
)
