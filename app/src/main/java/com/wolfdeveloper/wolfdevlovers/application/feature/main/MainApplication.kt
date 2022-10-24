package com.wolfdeveloper.wolfdevlovers.application.feature.main

import android.app.Application
import com.wolfdeveloper.wolfdevlovers.application.injector.sessionModule
import com.wolfdeveloper.wolfdevlovers.application.injector.apiModule
import com.wolfdeveloper.wolfdevlovers.application.injector.repositoryModule
import com.wolfdeveloper.wolfdevlovers.application.injector.useCaseModule
import com.wolfdeveloper.wolfdevlovers.application.injector.viewmodelModule
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