package com.example.androidtesttask.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlaceholderItem(
    val code: String,
    val name: String,
    val capital: String?,
    val native: String,
    val currency: String?,
    val continent:String
) : Parcelable {
    override fun toString(): String = name
}