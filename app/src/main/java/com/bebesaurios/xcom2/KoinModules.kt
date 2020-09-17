package com.bebesaurios.xcom2

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AssetHelper(androidContext().assets) }
    single { Database(get()) }
}
