package com.example.androidtesttask.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Country(
    @PrimaryKey
    @ColumnInfo(name = "code")
    var code: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "capital")
    var capital: String = "",
    @ColumnInfo(name = "native")
    var native: String = "",
    @ColumnInfo(name = "currency")
    var currency: String = ""
) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Long = 0L
}