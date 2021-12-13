package com.learnig.android.mydata.data.dao

import androidx.room.*


@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: T): Long

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)


}