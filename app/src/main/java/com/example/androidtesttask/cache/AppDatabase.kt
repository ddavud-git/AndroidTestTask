package com.example.androidtesttask.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtesttask.cache.dao.CountryDao
import com.example.androidtesttask.entity.Country

@Database(
    entities = [Country::class], version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getCountryDao():CountryDao
}