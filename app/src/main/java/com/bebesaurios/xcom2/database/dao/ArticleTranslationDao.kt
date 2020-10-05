package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.ArticleTranslationEntity

@Dao
interface ArticleTranslationDao : BaseDao<ArticleTranslationEntity> {
    @Query("SELECT * from articleTranslations")
    fun getAll() : List<ArticleTranslationEntity>

    @Query("DELETE from articleTranslations")
    fun deleteAll()

}