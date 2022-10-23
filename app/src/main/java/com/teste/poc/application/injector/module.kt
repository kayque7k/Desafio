package com.teste.poc.application.injector

import com.google.gson.Gson
import com.teste.poc.application.data.api.UserApi
import com.teste.poc.application.data.repository.UserRepositoryImpl
import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.application.feature.detail.DetailViewModel
import com.teste.poc.application.feature.main.MainViewModel
import com.teste.poc.application.feature.menu.MenuViewModel
import com.teste.poc.application.usecase.DetailsPersonUseCase
import com.teste.poc.application.usecase.UserUseCase
import com.teste.poc.coreapi.retrofit
import com.teste.poc.coreapi.session.ISessionOutput
import com.teste.poc.coreapi.session.ISessioInput
import com.teste.poc.coreapi.session.Session
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { MainViewModel() }
    viewModel { MenuViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val useCaseModule = module {
    factory { UserUseCase(get()) }
    factory { DetailsPersonUseCase(get()) }
}

val repositoryModule = module {
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}

val apiModule = module {
    factory<UserApi> { retrofit().create(UserApi::class.java) }
}

val sessionModule = module {
    factory<ISessionOutput> { Session(androidContext(), Gson()) }
    factory<ISessioInput> { Session(androidContext(), Gson()) }
}