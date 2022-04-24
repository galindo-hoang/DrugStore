package com.example.drugstore.data.models

//@Entity(tableName = "Address")
data class Address(
//    @PrimaryKey(autoGenerate = true)
//    var id:Int = 0,
    var longitude:Double = 0.0,
    var latitude:Double = 0.0,
    var phoneNumber:String = "",
    var address: String = "",
    var title: String = "",
    var check:Boolean = false
) 
