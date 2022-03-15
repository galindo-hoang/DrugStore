package com.example.drugstore.models

import java.util.*
import kotlin.collections.ArrayList

data class User(
    private var UserID:String = "",
    private var UserName:String = "",
    private var Password:String = "",
    private var Name:String = "",
    // 0: other
    // 1: female
    // 2: male
    private var Gender:Int = 0,
    private var Address:String = "",
    private var Birthday:Date = Date(),
    private var PhoneNumber:String = "",
    private var UserImage:String = "",
    private var Point:Float = 0.0f,
    // 0: User
    // 1: Shipper
    // 2: Admin
    private var Permission:Int = 0,
    // list of productID
    private var FavoriteProducts: ArrayList<String> = arrayListOf()
)
