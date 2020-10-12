package com.bebesaurios.xcom2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bebesaurios.xcom2.database.dao.*
import com.bebesaurios.xcom2.database.entities.*

@Database(entities = [
    ArticleEntity::class,
    KeywordEntity::class,
    SearchEntity::class,
    ArticleContentEntity::class,
    ConfigurationEntity::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
    abstract fun keywordDao() : KeywordDao
    abstract fun searchDao() : SearchDao
    abstract fun articleContentDao() : ArticleContentDao
    abstract fun configurationDao() : ConfigurationDao
}