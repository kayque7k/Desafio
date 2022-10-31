package com.wolfdeveloper.wolfdevlovers.application.injector

import com.google.gson.Gson
import com.wolfdeveloper.wolfdevlovers.application.data.api.LoverApi
import com.wolfdeveloper.wolfdevlovers.application.data.api.UserApi
import com.wolfdeveloper.wolfdevlovers.application.data.repository.PostRepositoryImpl
import com.wolfdeveloper.wolfdevlovers.application.data.repository.UserRepositoryImpl
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.PostRepository
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.UserRepository
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.detail.DetailViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel
import com.wolfdeveloper.wolfdevlovers.application.usecase.UserUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DetailsUserUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardInsertUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardBackgroundUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardProfileUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardImageLoverUseCase
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.retrofit
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessioInput
import com.wolfdeveloper.wolfdevlovers.coreapi.session.Session
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel {
        MainViewModel()
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
    factory { DashboardImageLoverUseCase(postRepository = get()) }
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
    factory<PostRepository> {
        PostRepositoryImpl(
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