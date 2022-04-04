package com.example.drugstore

import kotlinx.coroutines.*
import javax.inject.Inject


class b {
    suspend fun a()  {

        delay(3000)
        println(8)
    }
}
class c {
    fun a()  {
        println(3)
    }
}

//class c(){
//    suspend fun a(B:b){
//        B.a()
//    }
//}
//
class D @Inject constructor() {
    val c = 5
    val getC: Int get() = c
}

class E{
    @Inject
    lateinit var d:D
}

fun main(){
    val e = E()
    print(e.d.getC)
}