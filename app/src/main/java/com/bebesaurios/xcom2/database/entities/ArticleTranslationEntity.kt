package com.bebesaurios.xcom2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articleTranslations")
data class ArticleTranslationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "locale")
    val locale: String,
    @ColumnInfo(name = "translation")
    val translation: String
)