package com.teste.poc.application.injector

import com.google.gson.Gson
import com.teste.poc.application.data.api.LoverApi
import com.teste.poc.application.data.api.UserApi
import com.teste.poc.application.data.repository.LoverRepositoryImpl
import com.teste.poc.application.data.repository.UserRepositoryImpl
import com.teste.poc.application.domain.repository.LoverRepository
import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.application.feature.dashboard.DashboardViewModel
import com.teste.poc.application.feature.detail.DetailViewModel
import com.teste.poc.application.feature.main.MainViewModel
import com.teste.poc.application.feature.menu.MenuViewModel
import com.teste.poc.application.usecase.UserUseCase
import com.teste.poc.application.usecase.DetailsUserUseCase
import com.teste.poc.application.usecase.DashboardInsertUseCase
import com.teste.poc.application.usecase.DashboardBackgroundUseCase
import com.teste.poc.application.usecase.DashboardProfileUseCase
import com.teste.poc.application.usecase.DashboardImageLoverUseCase
import com.teste.poc.coreapi.retrofit
import com.teste.poc.coreapi.session.ISessionOutput
import com.teste.poc.coreapi.session.ISessioInput
import com.teste.poc.coreapi.session.Session
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel {
        MainViewModel(
            output = get()
        )
    }
    viewModel {
        DashboardViewModel(
            dashboardInsertUseCase = get(),
            dashboardBackgroundUseCase = get(),
            dashboardImageLoverUseCase = get(),
            dashboardProfileUseCase = get(),
            input = get(),
            output = get()
        )
    }
    viewModel { MenuViewModel(
        userUseCase = get(),
        output = get()
    ) }
    viewModel { DetailViewModel(detailsUserUseCase = get()) }
}

val useCaseModule = module {
    factory {
        UserUseCase(
            userRepository = get(),
            output = get()
        )
    }
    factory {
        DetailsUserUseCase(
            userRepository = get(),
            output = get()
        )
    }
    factory { DashboardInsertUseCase(userRepository = get()) }
    factory { DashboardBackgroundUseCase(userRepository = get()) }
    factory { DashboardProfileUseCase(userRepository = get()) }
    factory { DashboardImageLoverUseCase(loverRepository = get()) }
}

val repositoryModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(
            productApi = get(),
            input = get(),
            output = get(),
            context = androidContext()
        )
    }
    factory<LoverRepository> {
        LoverRepositoryImpl(
            loverApi = get(),
            context = androidContext()
        )
    }
}

val apiModule = module {
    factory<UserApi> { retrofit().create(UserApi::class.java) }
    factory<LoverApi> { retrofit().create(LoverApi::class.java) }
}

val sessionModule = module {
    factory<ISessionOutput> {
        Session(
            context = androidContext(),
            gson = Gson()
        )
    }
    factory<ISessioInput> {
        Session(
            context = androidContext(),
            gson = Gson()
        )
    }
}