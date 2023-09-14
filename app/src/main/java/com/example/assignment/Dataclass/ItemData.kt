package com.example.assignment.Dataclass

import android.os.Parcel
import android.os.Parcelable

data class ItemData(
    val id: Long,
    var itemName: String,
    var itemQty: Int,
    var itemPrice: Double,
    var orderId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(itemName)
        parcel.writeInt(itemQty)
        parcel.writeDouble(itemPrice)
        parcel.writeInt(orderId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemData> {
        override fun createFromParcel(parcel: Parcel): ItemData {
            return ItemData(parcel)
        }

        override fun newArray(size: Int): Array<ItemData?> {
            return arrayOfNulls(size)
        }
    }
}
