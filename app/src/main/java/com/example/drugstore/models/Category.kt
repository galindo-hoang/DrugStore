package com.example.drugstore.models

import android.os.Parcel
import android.os.Parcelable

data class Category(
    private var CatID:String = "",
    var CatName:String = "",
    private var CatImage:String = "",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel, p1: Int) = with(p0) {
        p0.writeString(CatID)
        p0.writeString(CatName)
        p0.writeString(CatImage)
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}
