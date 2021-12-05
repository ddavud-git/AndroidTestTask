package com.example.androidtesttask.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtesttask.entity.Country
import kotlinx.coroutines.Deferred

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //Replace or Ignore ?  = it depends on input data stability
     fun insert(list: List<Country>):List<Long>

    @Query("select * from country")
     fun getList():LiveData<List<Country>>

}