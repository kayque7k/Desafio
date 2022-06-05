package com.teste.poc.application.injector

import com.teste.poc.application.data.api.CategoryApi
import com.teste.poc.application.data.api.ProductApi
import com.teste.poc.application.data.mockapi.CategoryApiMock
import com.teste.poc.application.data.mockapi.ProductApiMock
import com.teste.poc.application.data.repository.CategoryRepositoryImpl
import com.teste.poc.application.data.repository.ProductRepositoryImpl
import com.teste.poc.application.domain.repository.CategoryRepository
import com.teste.poc.application.domain.repository.ProductRepository
import com.teste.poc.application.feature.detail.DetailViewModel
import com.teste.poc.application.feature.main.MainViewModel
import com.teste.poc.application.feature.menu.MenuViewModel
import com.teste.poc.application.usecase.CategoryUseCase
import com.teste.poc.application.usecase.ProductCategoryUseCase
import com.teste.poc.application.usecase.ProductUseCase
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