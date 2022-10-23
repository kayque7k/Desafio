package com.teste.poc.application.feature.main

import android.app.Application
import com.teste.poc.application.injector.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@MainApplication)
            modules(
                sessionModule,
                apiModule,
                repositoryModule,
                useCaseModule,
                viewmodelModule
            )
        }
    }
}