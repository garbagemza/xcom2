package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.ArticleEntity

@Dao
interface ArticleDao : BaseDao<ArticleEntity> {
    @Query("SELECT * from articles")
    fun getAll() : List<ArticleEntity>
}