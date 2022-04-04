package com.example.drugstore.data.models

import android.os.Parcel
import android.os.Parcelable

data class Product(
    var ProID:Int = 0,
    var CatID:Int = 0,
    var Price:Int = 0,
    var ProName:String = "",
    var Quantity:Int = 0,
    var ProImage:String = "",
    var Description:String = "",
    var IngredientList:ArrayList<Ingredient> = arrayListOf(),
    var NutritionList:ArrayList<Nutrition> = arrayListOf()
)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Ingredient.CREATOR)!!,
        parcel.createTypedArrayList(Nutrition.CREATOR)!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) = with(p0) {
        p0!!.writeInt(ProID)
        p0.writeInt(CatID)
        p0.writeInt(Price)
        p0.writeInt(Quantity)
        p0.writeString(ProImage)
        p0.writeString(Description)
        p0.writeString(ProName)
        p0.writeTypedList(IngredientList)
        p0.writeTypedList(NutritionList)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
