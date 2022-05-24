package com.teste.renner.application.feature.main

import android.app.Application
import com.teste.renner.application.injector.apiModule
import com.teste.renner.application.injector.repositoryModule
import com.teste.renner.application.injector.useCaseModule
import com.teste.renner.application.injector.viewmodelModule
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