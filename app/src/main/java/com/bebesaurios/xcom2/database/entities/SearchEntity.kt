package com.bebesaurios.xcom2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searches")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "keyword")
    val keyword: String,
    @ColumnInfo(name = "article")
    val article: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "weight")
    val weight: Int
)