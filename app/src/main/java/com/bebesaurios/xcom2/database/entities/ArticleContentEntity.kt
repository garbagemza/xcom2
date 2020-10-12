package com.bebesaurios.xcom2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articleContents")
data class ArticleContentEntity(
    @PrimaryKey
    @ColumnInfo(name = "article_key")
    val key: String,

    @ColumnInfo(name = "content")
    val content: String
)