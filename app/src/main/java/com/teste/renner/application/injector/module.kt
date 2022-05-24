package com.teste.renner.application.injector

import com.teste.renner.application.data.api.CategoryApi
import com.teste.renner.application.data.api.ProductApi
import com.teste.renner.application.data.mockapi.CategoryApiMock
import com.teste.renner.application.data.mockapi.ProductApiMock
import com.teste.renner.application.data.repository.CategoryRepositoryImpl
import com.teste.renner.application.data.repository.ProductRepositoryImpl
import com.teste.renner.application.domain.repository.CategoryRepository
import com.teste.renner.application.domain.repository.ProductRepository
import com.teste.renner.application.feature.detail.DetailViewModel
import com.teste.renner.application.feature.main.MainViewModel
import com.teste.renner.application.feature.menu.MenuViewModel
import com.teste.renner.application.usecase.CategoryUseCase
import com.teste.renner.application.usecase.ProductCategoryUseCase
import com.teste.renner.application.usecase.ProductUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { MainViewModel() }
    viewModel { MenuViewModel(get(), get()) }
    viewModel { DetailViewModel(get()) }
}

val useCaseModule = module {
    factory { CategoryUseCase(get()) }
    factory { ProductCategoryUseCase(get()) }
    factory { ProductUseCase(get()) }
}

val repositoryModule = module {
    factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
    factory<ProductRepository> { ProductRepositoryImpl(get()) }
}

val apiModule = module {
    factory<CategoryApi> { CategoryApiMock() }
    factory<ProductApi> { ProductApiMock() }
}