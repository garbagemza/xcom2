package com.bebesaurios.xcom2

import androidx.room.Room
import com.bebesaurios.xcom2.database.AppDatabase
import com.bebesaurios.xcom2.database.Database
import com.bebesaurios.xcom2.database.DatabaseFeeder
import com.bebesaurios.xcom2.database.DatabaseModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val persistenceModule = module {
    single { AssetHelper(androidContext().assets) }
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "data.db")
            .allowMainThreadQueries()
            .build()
    }
    single { DatabaseModel(get()) } // TODO: refactor this model
    single { DatabaseFeeder(get(), get()) }
    single { Database(get()) }
}