package com.example.drugstore.data.models

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Order(
    var OrderID: String = "",
    var UserID: String = "",
    var DateOrder: Date = Date(),
    var ShipperID: String = "",
    // false : on Shipping
    // true : Done
    var Status:Boolean = false,
    var Address:Address = Address(),
    var Point:Int = 0,
    // 0 : cash
    // 1 : visa
    // 2 : paypal
    // 3 : apple pay
    var PaymentID:Int = 0,
    var DateReceive:Date = Date(),
    // <ProductID,Quantity>
    var ProductList: ArrayList<CartProduct> = ArrayList()
)
