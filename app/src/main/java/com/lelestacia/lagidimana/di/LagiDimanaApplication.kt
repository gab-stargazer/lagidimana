package com.lelestacia.lagidimana.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.logger.SLF4JLogger

class LagiDimanaApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LagiDimanaApplication)
            modules(module)
            logger(SLF4JLogger())
            workManagerFactory()
        }
    }
}