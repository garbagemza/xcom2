package com.bebesaurios.xcom2

import android.content.Context
import androidx.room.Room
import com.bebesaurios.xcom2.bootstrap.BootstrapViewModel
import com.bebesaurios.xcom2.database.AppDatabase
import com.bebesaurios.xcom2.database.Database
import com.bebesaurios.xcom2.database.DatabaseFeeder
import com.bebesaurios.xcom2.database.DatabaseModel
import com.bebesaurios.xcom2.service.retrofit.FileService
import com.bebesaurios.xcom2.util.Preferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val persistenceModule = module {

    //single { AssetHelper(androidContext().assets) }
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "data.db")
            .allowMainThreadQueries()
            .build()
    }

    single { (model: DatabaseModel) -> DatabaseFeeder(model, get()) }
    single { Database(get()) }

    // for preferences injection
    single { androidApplication().getSharedPreferences("preferences", Context.MODE_PRIVATE) }
    single { Preferences(get()) }

    viewModel {
        BootstrapViewModel()
    }
}

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/xcom-2.appspot.com/o/")
            .build()
        retrofit.create(FileService::class.java) as FileService
    }
}