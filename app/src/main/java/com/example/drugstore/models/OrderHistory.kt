package com.example.drugstore.models

import com.example.drugstore.R

data class OrderHistory(
     var dateOrder:String = "",
     var total:Float = 0.0f,
     var proName:String = "",
     var quantity:Int = 0,
     var status:String = "",
     var img:Int=0,
     var address:String=""
){
     var orderID:String = ""
     var repeatOrder:Boolean=false
     companion object{
          fun createListOrderHistory():ArrayList<OrderHistory>{
               val list:ArrayList<OrderHistory> = arrayListOf()
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
               ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               list.add(OrderHistory("20 March 2022 at 10:00AM", 100F,"Kit Test Covid"
                    ,2,"Delivered", R.drawable.trending,"227 Nguyen Van Cu, quan 5"))
               return list
          }
     }
}
