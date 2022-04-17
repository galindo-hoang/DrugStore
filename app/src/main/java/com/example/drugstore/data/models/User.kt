package com.example.drugstore.data.models

import java.util.*
import kotlin.collections.ArrayList

data class User(
    var UserID:String = "",
    var UserName:String = "",
//    var Password:String = "",
//    var Name:String = "",
    // 0: other
    // 1: female
    // 2: male
    var Gender:Int = 0,
    var Address:String = "",
    var Birthday:Date = Date(),
    var PhoneNumber:String = "",
    var UserImage:String = "",
    var Point:Float = 0.0f,
    // 0: User
    // 1: Shipper
    // 2: Admin
    var Permission:Int = 0,
    // list of productID
    var FavoriteProducts: ArrayList<Int> = arrayListOf()
)
