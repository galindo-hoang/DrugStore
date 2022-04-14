package com.example.drugstore.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    var NutritionID:Int = 0,
    var NutritionName:String = "",
    var Unit:String = "mg",
)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) = with(p0) {
        p0!!.writeString(NutritionName)
        p0.writeInt(NutritionID)
        p0.writeString(Unit)
    }

    companion object CREATOR : Parcelable.Creator<Nutrition> {
        override fun createFromParcel(parcel: Parcel): Nutrition {
            return Nutrition(parcel)
        }

        override fun newArray(size: Int): Array<Nutrition?> {
            return arrayOfNulls(size)
        }
    }
}
