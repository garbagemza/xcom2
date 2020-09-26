package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.SearchEntity

@Dao
interface SearchDao : BaseDao<SearchEntity> {
    @Query("SELECT * from searches")
    fun getAll() : List<SearchEntity>

    @Query("SELECT * from searches where keyword = (SELECT DISTINCT key from keywords WHERE word like :searchResult) ORDER by weight DESC")
    fun find(searchResult: String) : List<SearchEntity>
}