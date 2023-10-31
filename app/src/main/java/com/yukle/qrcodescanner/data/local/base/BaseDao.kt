package com.yukle.qrcodescanner.data.local.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<E> {
    @Insert
    fun insert(entity: E)

    @Insert
    fun insert(entity: List<E>?)

    @Update
    fun update(entity: E)

    @Update
    fun update(vararg entity: E)

    @Delete
    fun delete(entity: E)

    @Delete
    fun delete(vararg entity: E)
}