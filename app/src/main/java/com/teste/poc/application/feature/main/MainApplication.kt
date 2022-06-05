package com.teste.poc.application.feature.main

import android.app.Application
import com.teste.poc.application.injector.apiModule
import com.teste.poc.application.injector.repositoryModule
import com.teste.poc.application.injector.useCaseModule
import com.teste.poc.application.injector.viewmodelModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                apiModule,
                repositoryModule,
                useCaseModule,
                viewmodelModule
            )
        }
    }
}