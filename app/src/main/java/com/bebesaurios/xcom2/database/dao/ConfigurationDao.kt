package com.bebesaurios.xcom2.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bebesaurios.xcom2.database.dao.base.BaseDao
import com.bebesaurios.xcom2.database.entities.ConfigurationEntity

@Dao
interface ConfigurationDao : BaseDao<ConfigurationEntity> {

    @Query("SELECT * from configurations where `key` = :key")
    fun getConfiguration(key: String) : ConfigurationEntity?


}