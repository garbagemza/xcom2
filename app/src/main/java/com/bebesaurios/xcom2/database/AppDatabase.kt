package com.bebesaurios.xcom2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bebesaurios.xcom2.database.dao.ArticleDao
import com.bebesaurios.xcom2.database.dao.ArticleTranslationDao
import com.bebesaurios.xcom2.database.dao.KeywordDao
import com.bebesaurios.xcom2.database.dao.SearchDao
import com.bebesaurios.xcom2.database.entities.ArticleEntity
import com.bebesaurios.xcom2.database.entities.ArticleTranslationEntity
import com.bebesaurios.xcom2.database.entities.KeywordEntity
import com.bebesaurios.xcom2.database.entities.SearchEntity

@Database(entities = [ArticleEntity::class, KeywordEntity::class, ArticleTranslationEntity::class, SearchEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
    abstract fun keywordDao() : KeywordDao
    abstract fun articleTranslationDao() : ArticleTranslationDao
    abstract fun searchDao() : SearchDao
}