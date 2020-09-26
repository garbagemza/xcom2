package com.bebesaurios.xcom2.database

import com.bebesaurios.xcom2.database.entities.SearchEntity


class Database(private val db: AppDatabase) {

    fun findSearchResults(searchText: String) : List<SearchEntity> {
        val likeArg = "%$searchText%"
        return db.searchDao().find(likeArg)
    }
}