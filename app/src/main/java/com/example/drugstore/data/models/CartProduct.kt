package com.example.drugstore.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartProduct(
    @PrimaryKey(autoGenerate = false)
    var ProID:Int = 0,

    var ProName:String = "",
    var Quantity:Int = 0,
    var Price:Int = 0,
    var ProImage:String = "",
//     <ProductID,Quantity>
//    var ProductList: HashMap<Int,Int> = hashMapOf()
)