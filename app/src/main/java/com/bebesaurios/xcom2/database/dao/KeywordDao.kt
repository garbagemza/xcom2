package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.KeywordEntity

@Dao
interface KeywordDao : BaseDao<KeywordEntity> {
    @Query("SELECT * from keywords")
    fun getAll() : List<KeywordEntity>

}