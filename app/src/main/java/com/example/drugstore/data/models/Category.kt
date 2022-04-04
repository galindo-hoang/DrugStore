package com.example.drugstore.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    var CatID:Int = 0,
    var CatName:String = "",
    var CatImage:String = "",
)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel, p1: Int) = with(p0) {
        p0.writeInt(CatID)
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
