package com.example.drugstore.models

import com.example.drugstore.R

data class Cart(
    var Total:Float = 0.0f,
    var img:Int=0,
    val Quantity:Int=0,

    val proName:String="",
) {
    val CheckPoint:Boolean=false
    val CartID:String=""
    val ProID:String=""
    val UserID:String=""

    companion object {
        fun createListOfCart(): ArrayList<Cart> {
            val list: ArrayList<Cart> = arrayListOf()
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                 "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            list.add(Cart(100F,
                R.drawable.trending,
                2,
                "Covid-19 Molnupiravir"))
            return list
        }
    }
}
