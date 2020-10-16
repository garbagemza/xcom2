package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.ArticleContentEntity

@Dao
interface ArticleContentDao : BaseDao<ArticleContentEntity> {

    @Query("SELECT * from articleContents where article_key = :articleKey")
    fun get(articleKey: String): ArticleContentEntity?

}