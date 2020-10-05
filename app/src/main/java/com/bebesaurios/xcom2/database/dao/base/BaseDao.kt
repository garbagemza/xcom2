package com.bebesaurios.xcom2.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<in E> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(entity: E)
}