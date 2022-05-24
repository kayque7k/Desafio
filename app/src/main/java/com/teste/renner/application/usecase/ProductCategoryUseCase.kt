package com.teste.renner.application.usecase

import com.teste.renner.application.data.repository.ProductRepositoryImpl
import com.teste.renner.application.domain.repository.ProductRepository
import com.teste.renner.commons.extensions.safeRunDispatcher

class ProductCategoryUseCase(
    val productRepository: ProductRepository
) {
    suspend fun execute(idCategory: Int) = safeRunDispatcher {
        productRepository.getProducts(idCategory)
    }
}