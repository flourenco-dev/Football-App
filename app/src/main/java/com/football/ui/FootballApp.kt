package com.football.ui

import android.app.Application
import com.football.BuildConfig
import com.football.core.injection.coreModule
import com.football.core.network.injection.networkModule
import com.football.ui.injection.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class FootballApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@FootballApp)
            modules(
                networkModule,
                coreModule,
                uiModule
            )
        }
    }
}