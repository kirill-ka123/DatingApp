package com.example.datingapp

import android.app.Application
import com.example.datingapp.presentation.di.appModule
import com.example.datingapp.presentation.di.dataModule
import com.example.datingapp.presentation.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DatingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DatingApplication)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}