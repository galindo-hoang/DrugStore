package com.example.drugstore.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Exclude

data class Product(
    var ProID: Int = 0,
    var CatID: Int = 0,
    var Price: Int = 0,
    var Quantity: Int = 0,
    var ProName: String = "",
    var ProImage: String = "",
    var Description: String = "",
    var IngredientList: ArrayList<Ingredient> = arrayListOf(),
    var NutritionList: ArrayList<Nutrition> = arrayListOf(),

    @JvmField
    @Exclude
    var category: Category? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Ingredient.CREATOR)!!,
        parcel.createTypedArrayList(Nutrition.CREATOR)!!
    ) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) = with(p0) {
        this!!.writeInt(ProID)
        this.writeInt(CatID)
        this.writeInt(Price)
        this.writeInt(Quantity)
        this.writeString(ProName)
        this.writeString(ProImage)
        this.writeString(Description)
        this.writeTypedList(IngredientList)
        this.writeTypedList(NutritionList)
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