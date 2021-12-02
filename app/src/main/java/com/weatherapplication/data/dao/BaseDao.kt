package com.weatherapplication.data.dao

import androidx.room.*


@Dao
interface BaseDao<T> {



    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)
}