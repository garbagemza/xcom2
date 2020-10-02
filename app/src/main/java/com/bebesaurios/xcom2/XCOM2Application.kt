package com.bebesaurios.xcom2

import android.app.Application
import com.bebesaurios.xcom2.database.DatabaseFeeder
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class XCOM2Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@XCOM2Application)
            modules(persistenceModule, networkModule)
        }

        //val feeder: DatabaseFeeder by inject()
        //feeder.start()

        // TODO: move to a content provider
        Stetho.initializeWithDefaults(this);
    }
}